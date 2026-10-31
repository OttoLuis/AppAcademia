package com.otto.fitIA.dto;

import java.util.List;

public record TreinoGeradoIA(
        String nomeTreino,
        List<ExercicioGeradoIA> exercicios
) {}