
package com.otto.fitIA.service;

import com.otto.fitIA.models.PlanoExercicio;
import com.otto.fitIA.models.PlanoTreino;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.PlanoExercicioRepository;
import com.otto.fitIA.repository.PlanoTreinoRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlanoExercicioService {

    private final PlanoExercicioRepository planoExercicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoTreinoRepository planoTreinoRepository;

    public PlanoExercicioService(
            PlanoExercicioRepository planoExercicioRepository,
            UsuarioRepository usuarioRepository,
            PlanoTreinoRepository planoTreinoRepository
    ) {
        this.planoExercicioRepository = planoExercicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.planoTreinoRepository = planoTreinoRepository;
    }

    public PlanoExercicio cadastrar(
            PlanoExercicio planoExercicio,
            Long planoTreinoId
    ) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
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

        PlanoTreino planoTreino = planoTreinoRepository
                .findById(planoTreinoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano de treino não encontrado"
                ));

        if (planoTreino.getUsuario() == null
                || !planoTreino.getUsuario().getId()
                .equals(usuario.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para usar este plano de treino"
            );
        }

        planoExercicio.setPlanoTreino(planoTreino);

        return planoExercicioRepository.save(planoExercicio);
    }
}
