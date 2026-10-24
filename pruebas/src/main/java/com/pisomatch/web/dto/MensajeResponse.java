package com.pisomatch.web.dto;

import com.pisomatch.domain.Mensaje;

import java.time.Instant;

/**
 * Representación pública de un mensaje de chat.
 */
public record MensajeResponse(
        Long id,
        Long emisorId,
        Long receptorId,
        String contenido,
        Instant fecha,
        boolean leido,
        Instant fechaLectura) {

    public static MensajeResponse from(Mensaje m) {
        return new MensajeResponse(
                m.getId(), m.getEmisorId(), m.getReceptorId(), m.getContenido(), m.getFecha(),
                m.isLeido(), m.getFechaLectura());
    }
}
