
package com.otto.fitIA.controller;

import com.otto.fitIA.dto.ExercicioResponse;
import com.otto.fitIA.models.Exercicio;
import com.otto.fitIA.service.ExercicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }

    // GET - listar todos
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    public ResponseEntity<List<ExercicioResponse>> listarTodos() {
        List<ExercicioResponse> exercicios = exercicioService.listarTodos()
                .stream()
                .map(this::converter)
                .toList();

        return ResponseEntity.ok(exercicios);
    }

    // GET - buscar por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    public ResponseEntity<ExercicioResponse> buscarPorId(
            @PathVariable Long id
    ) {
        Exercicio exercicio = exercicioService.buscarPorId(id);

        return ResponseEntity.ok(converter(exercicio));
    }

    // POST - cadastrar
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExercicioResponse> cadastrar(
            @RequestBody Exercicio exercicio
    ) {
        Exercicio salvo = exercicioService.cadastrar(exercicio);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter(salvo));
    }

    // PUT - atualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ExercicioResponse> atualizar(
            @PathVariable Long id,
            @RequestBody Exercicio exercicio
    ) {
        Exercicio atualizado = exercicioService.atualizar(id, exercicio);

        return ResponseEntity.ok(converter(atualizado));
    }

    // DELETE - excluir
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        exercicioService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    // Conversão para DTO
    private ExercicioResponse converter(Exercicio exercicio) {
        return new ExercicioResponse(
                exercicio.getId(),
                exercicio.getNomeExercicio(),
                exercicio.getGrupoMuscular(),
                exercicio.getDescricao(),
                exercicio.getEquipamento(),
                exercicio.getNivelDificuldade()
        );
    }
}
