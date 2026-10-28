package com.otto.fitIA.dto;



public record PlanoExercicioResponse(
        Long id,
        String diaTreino,
        Integer ordem,
        Integer series,
        String repeticoes,
        Integer segundosDeDescanso,
        Long planoTreinoId

) {
}
