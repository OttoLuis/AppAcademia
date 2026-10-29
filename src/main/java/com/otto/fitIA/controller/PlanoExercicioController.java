
package com.otto.fitIA.controller;

import com.otto.fitIA.models.PlanoExercicio;
import com.otto.fitIA.service.PlanoExercicioService;
import com.otto.fitIA.dto.PlanoExercicioResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plano-exercicio")
public class PlanoExercicioController {

    private final PlanoExercicioService planoExercicioService;

    public PlanoExercicioController(
            PlanoExercicioService planoExercicioService) {
        this.planoExercicioService = planoExercicioService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping
    public ResponseEntity<List<PlanoExercicioResponse>> listarTodos() {
        List<PlanoExercicioResponse> resposta =
                planoExercicioService.listarTodos()
                        .stream()
                        .map(this::converter)
                        .toList();

        return ResponseEntity.ok(resposta);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<PlanoExercicioResponse> buscarPorId(
            @PathVariable Long id) {

        PlanoExercicio planoExercicio =
                planoExercicioService.buscarPorId(id);

        return ResponseEntity.ok(converter(planoExercicio));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PostMapping
    public ResponseEntity<PlanoExercicioResponse> cadastrar(
            @RequestBody PlanoExercicio planoExercicio,
            @RequestParam Long exercicioId) {

        PlanoExercicio salvo =
                planoExercicioService.cadastrar(
                        planoExercicio,
                        exercicioId
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter(salvo));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<PlanoExercicioResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PlanoExercicio planoExercicio) {

        PlanoExercicio atualizado =
                planoExercicioService.atualizar(id, planoExercicio);

        return ResponseEntity.ok(converter(atualizado));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        planoExercicioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private PlanoExercicioResponse converter(
            PlanoExercicio planoExercicio) {

        return new PlanoExercicioResponse(
                planoExercicio.getId(),
                planoExercicio.getDiaTreino(),
                planoExercicio.getOrdem(),
                planoExercicio.getSeries(),
                planoExercicio.getRepeticoes(),
                planoExercicio.getSegundosDeDescanso(),
                planoExercicio.getPlanoTreino().getId()
        );
    }
}
