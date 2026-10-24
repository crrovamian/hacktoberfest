package com.pisomatch.repository;

import com.pisomatch.domain.PerfilConvivencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilConvivenciaRepository extends JpaRepository<PerfilConvivencia, Long> {

    Optional<PerfilConvivencia> findByUsuarioId(Long usuarioId);
}
