package com.pisomatch.repository;

import com.pisomatch.domain.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViviendaRepository extends JpaRepository<Vivienda, Long> {

    List<Vivienda> findByPropietarioId(Long propietarioId);
}
