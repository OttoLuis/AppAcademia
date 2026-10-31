
package com.otto.fitIA.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados da meta cadastrada pelo usuário")
public record MetaResponse(

        @Schema(
                description = "Identificador da meta",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Descrição da meta do usuário",
                example = "Atingir 70 kg"
        )
        String descricao,

        @Schema(
                description = "Data de início da meta",
                example = "2026-10-31"
        )
        LocalDate dataInicio,

        @Schema(
                description = "Prazo definido para alcançar a meta",
                example = "2027-01-31"
        )
        LocalDate prazo,

        @Schema(
                description = "Percentual de progresso da meta",
                example = "45.0"
        )
        Double progresso,

        @Schema(
                description = "Identificador do usuário associado à meta",
                example = "2"
        )
        Long usuarioId

) {
}
