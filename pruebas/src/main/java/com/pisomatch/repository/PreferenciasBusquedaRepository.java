package com.pisomatch.repository;

import com.pisomatch.domain.PreferenciasBusqueda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferenciasBusquedaRepository extends JpaRepository<PreferenciasBusqueda, Long> {

    Optional<PreferenciasBusqueda> findByUsuarioId(Long usuarioId);
}
