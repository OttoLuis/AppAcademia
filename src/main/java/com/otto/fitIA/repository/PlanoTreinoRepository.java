package com.otto.fitIA.repository;

import com.otto.fitIA.models.PlanoTreino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanoTreinoRepository extends JpaRepository<PlanoTreino, Long> {
    Optional<PlanoTreino> findByUsuarioId(Long usuarioId);
}
