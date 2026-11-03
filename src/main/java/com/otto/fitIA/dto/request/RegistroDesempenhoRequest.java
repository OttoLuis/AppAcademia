package com.otto.fitIA.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistroDesempenhoRequest(

        @NotNull(message = "A data do registro é obrigatória")
        LocalDate dataRegistro,

        @NotNull(message = "A carga utilizada é obrigatória")
        @PositiveOrZero(message = "A carga não pode ser negativa")
        BigDecimal cargaUtilizada,

        @NotNull(message = "A quantidade de séries é obrigatória")
        @Positive(message = "As séries devem ser maiores que zero")
        Integer seriesRealizadas,

        @NotNull(message = "As repetições são obrigatórias")
        @Positive(message = "As repetições devem ser maiores que zero")
        Integer repeticoesRealizadas,

        String observacao

) {}