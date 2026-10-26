package com.otto.fitIA.controller;


import com.otto.fitIA.dto.MetaResponse;
import com.otto.fitIA.models.Meta;
import com.otto.fitIA.repository.MetaRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import com.otto.fitIA.service.MetaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/meta")
public class MetaController {

    private final MetaRepository metaRepository;
    private final MetaService metaService;
    private final UsuarioRepository usuarioRepository;

    public MetaController(MetaRepository metaRepository, MetaService metaService, UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.metaService = metaService;
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @GetMapping
    public ResponseEntity<List<MetaResponse>> listarTodos() {
        List<MetaResponse> metas = metaService.listarTodos()
                .stream()
                .map(this::converter)
                .toList();

        return ResponseEntity.ok(metas);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PostMapping
    public ResponseEntity<MetaResponse> cadastrar(
            @RequestBody Meta meta) {

        Meta novaMeta = metaService.cadastrar(meta);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(converter(novaMeta));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        metaService.buscarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USUARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<MetaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody Meta meta) {

        Meta atualizado = metaService.atualizar(id, meta);

        return ResponseEntity.ok(converter(atualizado));
    }

    private MetaResponse converter(Meta meta) {
            return new MetaResponse(
                    meta.getId(),
                    meta.getDescricao(),
                    meta.getDataInicio(),
                    meta.getPrazo(),
                    meta.getProgresso(),
                    meta.getUsuario() != null
                            ? meta.getUsuario().getId()
                            : null
            );
        }


    }