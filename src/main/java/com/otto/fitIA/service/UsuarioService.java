
package com.otto.fitIA.service;

import com.otto.fitIA.models.Role;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.UsuarioRepository;
import com.otto.fitIA.projection.UserDetailsProjection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado: " + id
                ));
    }

    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        Usuario existente = buscarPorId(id);

        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());

        if (dadosAtualizados.getSenha() != null
                && !dadosAtualizados.getSenha().isBlank()) {
            existente.setSenha(dadosAtualizados.getSenha());
        }

        return usuarioRepository.save(existente);
    }

    public void excluir(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    public void validarAcessoAoUsuario(
            Long id,
            Authentication authentication
    ) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Autenticação necessária.");
        }

        if (ehAdmin(authentication)) {
            return;
        }

        Usuario atual = usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException(
                        "Usuário autenticado não encontrado."
                ));

        if (!atual.getId().equals(id)) {
            throw new AccessDeniedException(
                    "Você não pode acessar a conta de outro usuário."
            );
        }
    }

    public void exigirAdmin(Authentication authentication) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || !ehAdmin(authentication)) {
            throw new AccessDeniedException(
                    "Apenas administradores podem realizar esta operação."
            );
        }
    }

    private boolean ehAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN")
                );
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        List<UserDetailsProjection> resultado =
                usuarioRepository.searchUserAndRolesByEmail(username);

        if (resultado.isEmpty()) {
            throw new UsernameNotFoundException(
                    "Usuário não encontrado: " + username
            );
        }

        Usuario usuario = new Usuario();

        usuario.setEmail(username);
        usuario.setSenha(resultado.get(0).getPassword());

        for (UserDetailsProjection projection : resultado) {
            usuario.addRole(
                    new Role(
                            projection.getRoleId(),
                            projection.getAuthority()
                    )
            );
        }

        return usuario;
    }
}
