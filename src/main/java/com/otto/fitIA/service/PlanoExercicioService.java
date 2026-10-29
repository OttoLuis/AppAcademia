
package com.otto.fitIA.service;

import com.otto.fitIA.models.Exercicio;
import com.otto.fitIA.models.PlanoExercicio;
import com.otto.fitIA.models.PlanoTreino;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.ExercicioRepository;
import com.otto.fitIA.repository.PlanoExercicioRepository;
import com.otto.fitIA.repository.PlanoTreinoRepository;
import com.otto.fitIA.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlanoExercicioService {

    private final PlanoExercicioRepository planoExercicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoTreinoRepository planoTreinoRepository;
    private final ExercicioRepository exercicioRepository;

    public PlanoExercicioService(
            PlanoExercicioRepository planoExercicioRepository,
            UsuarioRepository usuarioRepository,
            PlanoTreinoRepository planoTreinoRepository,
            ExercicioRepository exercicioRepository) {

        this.planoExercicioRepository = planoExercicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.planoTreinoRepository = planoTreinoRepository;
        this.exercicioRepository = exercicioRepository;
    }

    public List<PlanoExercicio> listarTodos() {
        return planoExercicioRepository.findAll();
    }

    public PlanoExercicio buscarPorId(Long id) {
        return planoExercicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano de exercício não encontrado"
                ));
    }

    public PlanoExercicio cadastrar(
            PlanoExercicio dados,
            Long exercicioId) {

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
                .findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano de treino não encontrado para este usuário"
                ));

        Exercicio exercicio = exercicioRepository.findById(exercicioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exercício não encontrado"
                ));

        dados.setId(null);
        dados.setPlanoTreino(planoTreino);
        dados.setExercicio(exercicio);

        return planoExercicioRepository.save(dados);
    }

    public PlanoExercicio atualizar(
            Long id,
            PlanoExercicio dados) {

        PlanoExercicio existente = buscarPorId(id);

        existente.setDiaTreino(dados.getDiaTreino());
        existente.setOrdem(dados.getOrdem());
        existente.setSeries(dados.getSeries());
        existente.setRepeticoes(dados.getRepeticoes());
        existente.setSegundosDeDescanso(
                dados.getSegundosDeDescanso()
        );

        return planoExercicioRepository.save(existente);
    }

    public void excluir(Long id) {
        PlanoExercicio existente = buscarPorId(id);
        planoExercicioRepository.delete(existente);
    }
}
