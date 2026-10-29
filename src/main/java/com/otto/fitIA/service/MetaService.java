package com.otto.fitIA.service;

import com.otto.fitIA.exception.RecursoNaoEncontradoException;
import com.otto.fitIA.exception.RegraDeNegocioException;
import com.otto.fitIA.models.Meta;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.MetaRepository;
import com.otto.fitIA.repository.UsuarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    public MetaService(
            MetaRepository metaRepository,
            UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario obterUsuarioAutenticado() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RegraDeNegocioException(
                    "Usuário não autenticado"
            );
        }

        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado com e-mail: " + email
                        )
                );
    }

    private boolean isAdmin() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));
    }

    private void verificarPermissao(Meta meta) {
        Usuario usuario = obterUsuarioAutenticado();

        if (!isAdmin()
                && (meta.getUsuario() == null
                || !meta.getUsuario().getId().equals(usuario.getId()))) {
            throw new RegraDeNegocioException(
                    "Você não tem permissão para acessar esta meta"
            );
        }
    }

    public List<Meta> listarTodos() {
        if (isAdmin()) {
            return metaRepository.findAll();
        }

        Usuario usuario = obterUsuarioAutenticado();

        return metaRepository.findAll()
                .stream()
                .filter(meta -> meta.getUsuario() != null
                        && meta.getUsuario().getId().equals(usuario.getId()))
                .toList();
    }

    public Meta cadastrar(Meta meta) {
        Usuario usuario = obterUsuarioAutenticado();

        meta.setId(null);
        meta.setUsuario(usuario);

        return metaRepository.save(meta);
    }

    public Meta buscarPorId(Long id) {
        Meta meta = metaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Meta", id)
                );

        verificarPermissao(meta);

        return meta;
    }

    public Meta atualizar(Long id, Meta dados) {
        Meta meta = buscarPorId(id);

        meta.setDescricao(dados.getDescricao());
        meta.setDataInicio(dados.getDataInicio());
        meta.setPrazo(dados.getPrazo());
        meta.setProgresso(dados.getProgresso());

        return metaRepository.save(meta);
    }

    public void excluir(Long id) {
        Meta meta = buscarPorId(id);
        metaRepository.delete(meta);
    }
}