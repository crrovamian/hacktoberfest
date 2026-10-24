package com.pisomatch.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Datos para enviar un mensaje de chat a otro usuario.
 */
public record MensajeRequest(
        @NotNull Long receptorId,
        @NotBlank @Size(max = 2000) String contenido) {
}
