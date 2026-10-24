package com.pisomatch.repository;

import com.pisomatch.domain.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    /** Conversación entre dos usuarios (en ambos sentidos), ordenada cronológicamente. */
    List<Mensaje> findByEmisorIdInAndReceptorIdInOrderByFechaAsc(
            List<Long> emisores, List<Long> receptores);
}
