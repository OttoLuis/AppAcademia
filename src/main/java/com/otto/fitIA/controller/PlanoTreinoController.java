package com.otto.fitIA.controller;

import com.otto.fitIA.dto.PlanoTreinoResponse;
import com.otto.fitIA.models.PlanoTreino;
import com.otto.fitIA.service.PlanoTreinoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plano-treino")
public class PlanoTreinoController {

    private final PlanoTreinoService planoTreinoService;

    public PlanoTreinoController(PlanoTreinoService planoTreinoService) {
        this.planoTreinoService = planoTreinoService;
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping
    public ResponseEntity<List<PlanoTreinoResponse>> listarTodos() {
        List<PlanoTreinoResponse> perfis = planoTreinoService.listarTodos()
                .stream()
                .map(this::converter)
                .toList();

        return ResponseEntity.ok(perfis);
    }


    //Buscar Por id
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<PlanoTreinoResponse> buscarPorId(
            @PathVariable Long id) {

        PlanoTreino planoTreino = planoTreinoService.buscarPorId(id);

        return ResponseEntity.ok(converter(planoTreino));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PostMapping
    public ResponseEntity<PlanoTreinoResponse> cadastrar(
            @Valid @RequestBody PlanoTreino planoTreino) {

        PlanoTreino novoPlanoTreino = planoTreinoService.cadastrar(planoTreino);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter(novoPlanoTreino));
    }

    //Atualizar Dados Por id
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<PlanoTreinoResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlanoTreino planoTreino) {

        PlanoTreino atualizado = planoTreinoService.atualizar(id, planoTreino);

        return ResponseEntity.ok(converter(atualizado));
    }


    //Deletar Por Id
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        planoTreinoService.excluir(id);
        return ResponseEntity.noContent().build();
    }



    private PlanoTreinoResponse converter(PlanoTreino planoTreino) {
        return new PlanoTreinoResponse(
                planoTreino.getId(),
                planoTreino.getNomeTreino(),
                planoTreino.getObjetivo(),
                planoTreino.getDataInicio(),
                planoTreino.getDataFim(),
                planoTreino.getUsuario() != null
                        ? planoTreino.getUsuario().getId()
                        : null
        );
    }
}
