package com.otto.fitIA.dto.response;


public record ExercicioTreinoIAResponse(
        Long exercicioId,
        String nomeExercicio,
        String grupoMuscular,
        String diaTreino,
        Integer ordem,
        Integer series,
        String repeticoes,
        Integer segundosDeDescanso
) {
}