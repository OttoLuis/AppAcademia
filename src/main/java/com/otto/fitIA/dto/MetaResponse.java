package com.otto.fitIA.dto;

import java.time.LocalDate;

public record MetaResponse(
        Long id,
        String descricao,
        LocalDate dataInicio,
        LocalDate prazo,
        Double progresso,
        Long usuarioId


){

}


