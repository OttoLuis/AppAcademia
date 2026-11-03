package com.otto.fitIA.repository;

import com.otto.fitIA.models.PlanoExercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanoExercicioRepository extends JpaRepository<PlanoExercicio, Long> {

    List<PlanoExercicio> findByPlanoTreinoIdOrderByDiaTreinoAscOrdemAsc(Long planoTreinoId);

}