package com.otto.fitIA.dto.response;

import java.util.List;

public record GerarTreinoIAResponse(
        PlanoTreinoResponse plano,
        List<ExercicioTreinoIAResponse> exercicios
) {
}