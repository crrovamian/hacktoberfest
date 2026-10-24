package com.pisomatch.web.error;

import java.time.Instant;

/**
 * Cuerpo estándar de error para las respuestas de la API.
 */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path) {
}
