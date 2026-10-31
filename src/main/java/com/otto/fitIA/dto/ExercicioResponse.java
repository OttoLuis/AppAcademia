
package com.otto.fitIA.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de um exercício disponível no FitIA")
public record ExercicioResponse(

        @Schema(
                description = "Identificador do exercício",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome do exercício",
                example = "Supino reto"
        )
        String nomeExercicio,

        @Schema(
                description = "Grupo muscular trabalhado pelo exercício",
                example = "Peito"
        )
        String grupoMuscular,

        @Schema(
                description = "Descrição e orientações sobre a execução do exercício",
                example = "Exercício realizado com barra e banco para trabalhar os músculos do peitoral."
        )
        String descricao,

        @Schema(
                description = "Equipamento necessário para realizar o exercício",
                example = "Barra"
        )
        String equipamento,

        @Schema(
                description = "Nível de dificuldade do exercício",
                example = "INTERMEDIARIO"
        )
        String nivelDificuldade

) {
}
