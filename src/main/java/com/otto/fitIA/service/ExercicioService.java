
package com.otto.fitIA.service;

import com.otto.fitIA.models.Exercicio;
import com.otto.fitIA.repository.ExercicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;

    public ExercicioService(ExercicioRepository exercicioRepository) {
        this.exercicioRepository = exercicioRepository;
    }

    public List<Exercicio> listarTodos() {
        return exercicioRepository.findAll();
    }

    public Exercicio buscarPorId(Long id) {
        return exercicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exercício não encontrado"
                ));
    }

    public Exercicio cadastrar(Exercicio exercicio) {
        exercicio.setId(null);
        return exercicioRepository.save(exercicio);
    }

    public Exercicio atualizar(Long id, Exercicio dados) {
        Exercicio exercicio = buscarPorId(id);


        exercicio.setNomeExercicio(dados.getNomeExercicio());
        exercicio.setGrupoMuscular(dados.getGrupoMuscular());
        exercicio.setDescricao(dados.getDescricao());
        exercicio.setEquipamento(dados.getEquipamento());
        exercicio.setNivelDificuldade(dados.getNivelDificuldade());


        return exercicioRepository.save(exercicio);
    }

    public void excluir(Long id) {
        Exercicio exercicio = buscarPorId(id);
        exercicioRepository.delete(exercicio);
    }
}
