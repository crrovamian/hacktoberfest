package com.pisomatch.web.error;

/**
 * Se lanza cuando no existe el recurso solicitado (p. ej. un perfil inexistente).
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
