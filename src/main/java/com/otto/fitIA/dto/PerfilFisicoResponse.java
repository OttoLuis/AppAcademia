
package com.otto.fitIA.dto;

import com.otto.fitIA.enums.NivelExperiencia;
import com.otto.fitIA.enums.ObjetivoFisico;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados do perfil físico do usuário")
public record PerfilFisicoResponse(

        @Schema(
                description = "Identificador do perfil físico",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Objetivo físico do usuário",
                example = "HIPERTROFIA"
        )
        ObjetivoFisico objetivo,

        @Schema(
                description = "Nível de experiência do usuário"
        )
        NivelExperiencia experiencia,

        @Schema(
                description = "Quantidade de dias de treino por semana",
                example = "4"
        )
        Integer frequenciaSemanal,

        @Schema(
                description = "Tempo disponível para cada treino, em minutos",
                example = "60"
        )
        Integer tempoDisponivel,

        @Schema(
                description = "Equipamentos disponíveis para treinamento",
                example = "Academia completa"
        )
        String equipamentoDisponivel,

        @Schema(
                description = "Peso atual do usuário em quilogramas",
                example = "75.0"
        )
        Double peso,

        @Schema(
                description = "Altura do usuário em metros",
                example = "1.74"
        )
        Double altura,

        @Schema(
                description = "Idade do usuário em anos",
                example = "21"
        )
        Integer idade,

        @Schema(
                description = "Identificador do usuário associado ao perfil",
                example = "2"
        )
        Long usuarioId

) {
}
