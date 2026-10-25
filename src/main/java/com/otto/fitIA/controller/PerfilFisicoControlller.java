
package com.otto.fitIA.controller;

import com.otto.fitIA.dto.PerfilFisicoResponse;
import com.otto.fitIA.models.PerfilFisico;
import com.otto.fitIA.service.PerfilFisicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis-fisico")
public class PerfilFisicoControlller {

    private final PerfilFisicoService service;

    public PerfilFisicoControlller(PerfilFisicoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PostMapping
    public ResponseEntity<PerfilFisicoResponse> cadastrar(
            @RequestBody PerfilFisico perfil) {

        PerfilFisico novoPerfil = service.cadastrar(perfil);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter(novoPerfil));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping
    public ResponseEntity<List<PerfilFisicoResponse>> listarTodos() {
        List<PerfilFisicoResponse> perfis = service.listarTodos()
                .stream()
                .map(this::converter)
                .toList();

        return ResponseEntity.ok(perfis);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<PerfilFisicoResponse> buscarPorId(
            @PathVariable Long id) {

        PerfilFisico perfil = service.buscarPorId(id);

        return ResponseEntity.ok(converter(perfil));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilFisicoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PerfilFisico perfil) {

        PerfilFisico atualizado = service.atualizar(id, perfil);

        return ResponseEntity.ok(converter(atualizado));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
    private PerfilFisicoResponse converter(PerfilFisico perfil) {
        return new PerfilFisicoResponse(
                perfil.getId(),
                perfil.getObjetivo(),
                perfil.getExperiencia(),
                perfil.getFrequenciaSemanal(),
                perfil.getTempoDisponivel(),
                perfil.getEquipamentoDisponivel(),
                perfil.getPeso(),
                perfil.getAltura(),
                perfil.getIdade(),
                perfil.getUsuario() != null
                        ? perfil.getUsuario().getId()
                        : null
        );
    }
}
