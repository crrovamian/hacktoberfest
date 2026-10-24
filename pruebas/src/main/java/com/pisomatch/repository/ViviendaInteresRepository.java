package com.pisomatch.repository;

import com.pisomatch.domain.ViviendaInteres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViviendaInteresRepository extends JpaRepository<ViviendaInteres, Long> {

    List<ViviendaInteres> findByUsuarioId(Long usuarioId);

    List<ViviendaInteres> findByViviendaId(Long viviendaId);

    Optional<ViviendaInteres> findByUsuarioIdAndViviendaId(Long usuarioId, Long viviendaId);

    boolean existsByUsuarioIdAndViviendaId(Long usuarioId, Long viviendaId);

    void deleteByUsuarioIdAndViviendaId(Long usuarioId, Long viviendaId);
}
