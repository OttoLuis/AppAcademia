package com.otto.fitIA.dto;

public record ExercicioResponse(
        Long id,
        String nomeExercicio,
        String grupoMuscular,
        String descricao,
        String equipamento,
        String nivelDificuldade) {
}
