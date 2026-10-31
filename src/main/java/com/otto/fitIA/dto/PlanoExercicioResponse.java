
package com.otto.fitIA.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do exercício associado a um plano de treino")
public record PlanoExercicioResponse(

        @Schema(
                description = "Identificador do exercício dentro do plano",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Dia da semana em que o exercício será realizado",
                example = "SEGUNDA"
        )
        String diaTreino,

        @Schema(
                description = "Ordem de execução do exercício no treino",
                example = "1"
        )
        Integer ordem,

        @Schema(
                description = "Quantidade de séries do exercício",
                example = "4"
        )
        Integer series,

        @Schema(
                description = "Quantidade de repetições por série",
                example = "12"
        )
        String repeticoes,

        @Schema(
                description = "Tempo de descanso entre as séries, em segundos",
                example = "60"
        )
        Integer segundosDeDescanso,

        @Schema(
                description = "Identificador do plano de treino associado",
                example = "3"
        )
        Long planoTreinoId

) {
}
