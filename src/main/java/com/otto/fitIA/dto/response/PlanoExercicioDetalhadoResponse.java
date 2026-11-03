package com.otto.fitIA.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exercício detalhado dentro de um plano de treino")
public record PlanoExercicioDetalhadoResponse(

        Long id,
        String diaTreino,
        Integer ordem,
        Integer series,
        String repeticoes,
        Integer segundosDeDescanso,
        Long planoTreinoId,

        Long exercicioId,
        String nomeExercicio,
        String grupoMuscular,
        String descricao,
        String equipamento,
        String nivelDificuldade

) {
}