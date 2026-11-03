package com.otto.fitIA.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistroDesempenhoResponse(
        Long id,
        LocalDate dataRegistro,
        BigDecimal cargaUtilizada,
        Integer seriesRealizadas,
        Integer repeticoesRealizadas,
        String observacao,
        Long planoExercicioId,
        String nomeExercicio
) {}