package com.otto.fitIA.service;


import com.otto.fitIA.models.Meta;
import com.otto.fitIA.models.PerfilFisico;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.MetaRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    public MetaService(MetaRepository metaRepository, UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Meta> listarTodos() {
        return metaRepository.findAll();
    }

    public Meta cadastrar(Meta meta) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        meta.setUsuario(usuario);

        return metaRepository.save(meta);
    }

    public Meta buscarPorId(Long id) {
        return metaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Meta com esse ID não encontrada"
                ));
    }

    public void excluir(Long id) {
        Meta meta = buscarPorId(id);
        metaRepository.delete(meta);
    }



}
