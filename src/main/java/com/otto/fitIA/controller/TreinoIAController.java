package com.otto.fitIA.controller;

import com.otto.fitIA.dto.GerarTreinoIAResponse;
import com.otto.fitIA.service.TreinoIAService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia")
public class TreinoIAController {

    private final TreinoIAService treinoIAService;

    public TreinoIAController(TreinoIAService treinoIAService) {
        this.treinoIAService = treinoIAService;
    }

    @PostMapping("/treino")
    public GerarTreinoIAResponse gerarTreino() {
        return treinoIAService.gerarTreino();
    }
}