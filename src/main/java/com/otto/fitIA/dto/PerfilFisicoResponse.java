
package com.otto.fitIA.dto;

import com.otto.fitIA.enums.NivelExperiencia;
import com.otto.fitIA.enums.ObjetivoFisico;

public record PerfilFisicoResponse(
        Long id,
        ObjetivoFisico objetivo,
        NivelExperiencia experiencia,
        Integer frequenciaSemanal,
        Integer tempoDisponivel,
        String equipamentoDisponivel,
        Double peso,
        Double altura,
        Integer idade,
        Long usuarioId
) {
}
