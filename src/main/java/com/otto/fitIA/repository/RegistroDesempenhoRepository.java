package com.otto.fitIA.repository;

import com.otto.fitIA.models.RegistroDesempenho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroDesempenhoRepository extends JpaRepository<RegistroDesempenho, Long> {

    List<RegistroDesempenho> findByPlanoExercicioIdOrderByDataRegistroAsc(Long planoExercicioId);

}
