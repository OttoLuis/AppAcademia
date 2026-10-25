package com.otto.fitIA.dto;

import java.time.LocalDate;

public record PlanoTreinoResponse(
        Long Id,
        String nomeTreino,
        String objetivo,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long usuarioID
) {

}
