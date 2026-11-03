
package com.otto.fitIA.controller;

import com.otto.fitIA.dto.request.RegistroDesempenhoRequest;
import com.otto.fitIA.dto.response.RegistroDesempenhoResponse;
import com.otto.fitIA.service.RegistroDesempenhoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registro-desempenho")
public class RegistroDesempenhoController {

    private final RegistroDesempenhoService registroService;

    public RegistroDesempenhoController(
            RegistroDesempenhoService registroService
    ) {
        this.registroService = registroService;
    }

    @PostMapping("/{planoExercicioId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USUARIO', 'ROLE_ADMIN')")
    public ResponseEntity<RegistroDesempenhoResponse> cadastrar(
            @PathVariable Long planoExercicioId,
            @Valid @RequestBody RegistroDesempenhoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registroService.cadastrar(request, planoExercicioId));
    }

    @GetMapping("/{planoExercicioId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USUARIO', 'ROLE_ADMIN')")
    public ResponseEntity<List<RegistroDesempenhoResponse>> listarPorExercicio(
            @PathVariable Long planoExercicioId
    ) {
        return ResponseEntity.ok(
                registroService.listarPorExercicio(planoExercicioId)
        );
    }
}
