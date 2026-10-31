
package com.otto.fitIA.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do plano de treino do usuário")
public record PlanoTreinoResponse(

        @Schema(
                description = "Identificador do plano de treino",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome do plano de treino",
                example = "Treino ABC"
        )
        String nomeTreino,

        @Schema(
                description = "Objetivo principal do plano de treino",
                example = "HIPERTROFIA"
        )
        String objetivo,

        @Schema(
                description = "Data de início do plano de treino",
                example = "2026-10-31"
        )
        LocalDate dataInicio,

        @Schema(
                description = "Data de encerramento do plano de treino",
                example = "2027-01-31"
        )
        LocalDate dataFim,

        @Schema(
                description = "Identificador do usuário associado ao plano",
                example = "2"
        )
        Long usuarioId

) {
}
