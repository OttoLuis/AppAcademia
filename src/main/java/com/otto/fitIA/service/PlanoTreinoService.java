
package com.otto.fitIA.service;

import com.otto.fitIA.models.PlanoTreino;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.PlanoTreinoRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlanoTreinoService {

    private final UsuarioRepository usuarioRepository;
    private final PlanoTreinoRepository planoTreinoRepository;

    public PlanoTreinoService(
            UsuarioRepository usuarioRepository,
            PlanoTreinoRepository planoTreinoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.planoTreinoRepository = planoTreinoRepository;
    }

    private Usuario obterUsuarioAutenticado() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        return usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
    }

    private boolean isAdmin() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));
    }

    private void verificarPermissao(PlanoTreino planoTreino) {
        Usuario usuario = obterUsuarioAutenticado();

        if (!isAdmin()
                && (planoTreino.getUsuario() == null
                || !planoTreino.getUsuario().getId().equals(usuario.getId()))) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para acessar este plano de treino"
            );
        }
    }

    public PlanoTreino cadastrar(PlanoTreino planoTreino) {
        Usuario usuario = obterUsuarioAutenticado();

        planoTreino.setUsuario(usuario);

        return planoTreinoRepository.save(planoTreino);
    }

    public List<PlanoTreino> listarTodos() {
        if (isAdmin()) {
            return planoTreinoRepository.findAll();
        }

        Usuario usuario = obterUsuarioAutenticado();

        return planoTreinoRepository.findAll()
                .stream()
                .filter(plano -> plano.getUsuario() != null
                        && plano.getUsuario().getId().equals(usuario.getId()))
                .toList();
    }

    public PlanoTreino buscarPorId(Long id) {
        PlanoTreino planoTreino = planoTreinoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano de treino com esse id não encontrado"
                ));

        verificarPermissao(planoTreino);

        return planoTreino;
    }

    public PlanoTreino atualizar(Long id, PlanoTreino dados) {
        PlanoTreino planoTreino = buscarPorId(id);

        planoTreino.setNomeTreino(dados.getNomeTreino());
        planoTreino.setObjetivo(dados.getObjetivo());
        planoTreino.setDataInicio(dados.getDataInicio());
        planoTreino.setDataFim(dados.getDataFim());

        return planoTreinoRepository.save(planoTreino);
    }

    public void excluir(Long id) {
        PlanoTreino planoTreino = buscarPorId(id);
        planoTreinoRepository.delete(planoTreino);
    }
}
