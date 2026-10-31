package com.otto.fitIA.dto;

import java.util.List;

public record GerarTreinoIAResponse(
        PlanoTreinoResponse plano,
        List<ExercicioTreinoIAResponse> exercicios
) {
}