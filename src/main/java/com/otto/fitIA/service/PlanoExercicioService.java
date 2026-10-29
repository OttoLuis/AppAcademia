package com.otto.fitIA.service;

import com.otto.fitIA.exception.RecursoNaoEncontradoException;
import com.otto.fitIA.exception.RegraDeNegocioException;
import com.otto.fitIA.models.Exercicio;
import com.otto.fitIA.models.PlanoExercicio;
import com.otto.fitIA.models.PlanoTreino;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.ExercicioRepository;
import com.otto.fitIA.repository.PlanoExercicioRepository;
import com.otto.fitIA.repository.PlanoTreinoRepository;
import com.otto.fitIA.repository.UsuarioRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    private void verificarPermissao(PlanoExercicio planoExercicio) {
        Usuario usuario = obterUsuarioAutenticado();

        Long usuarioDonoId = planoExercicio.getPlanoTreino()
                .getUsuario()
                .getId();

        if (!usuarioDonoId.equals(usuario.getId())) {
            throw new RegraDeNegocioException(
                    "Você não tem permissão para acessar este exercício do plano"
            );
        }
    }

    public List<PlanoExercicio> listarTodos() {
        Usuario usuario = obterUsuarioAutenticado();

        return planoExercicioRepository.findAll().stream()
                .filter(item -> item.getPlanoTreino() != null
                        && item.getPlanoTreino().getUsuario() != null
                        && item.getPlanoTreino().getUsuario().getId()
                        .equals(usuario.getId()))
                .toList();
    }

    public PlanoExercicio buscarPorId(Long id) {
        PlanoExercicio planoExercicio = planoExercicioRepository
                .findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Exercício do plano", id
                        )
                );

        verificarPermissao(planoExercicio);

        return planoExercicio;
    }

    public PlanoExercicio cadastrar(
            PlanoExercicio dados,
            Long exercicioId) {

        Usuario usuario = obterUsuarioAutenticado();

        PlanoTreino planoTreino = planoTreinoRepository
                .findByUsuarioId(usuario.getId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Plano de treino do usuário"
                        )
                );

        Exercicio exercicio = exercicioRepository.findById(exercicioId)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Exercício", exercicioId
                        )
                );

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