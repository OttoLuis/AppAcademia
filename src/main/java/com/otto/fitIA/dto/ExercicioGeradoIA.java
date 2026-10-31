package com.otto.fitIA.dto;

public record ExercicioGeradoIA(
        Long exercicioId,
        String diaTreino,
        Integer ordem,
        Integer series,
        String repeticoes,
        Integer segundosDeDescanso
) {}