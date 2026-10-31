package com.otto.fitIA.repository;

import com.otto.fitIA.models.PerfilFisico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PerfilFisicoRepository extends JpaRepository<PerfilFisico, Long> {

    Optional<PerfilFisico> findByUsuarioId(Long usuarioId);

}
