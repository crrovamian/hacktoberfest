package com.pisomatch.web.dto;

/**
 * Resultado de deslizar: indica si se ha producido match y, en ese caso, con quién.
 */
public record SwipeResult(
        boolean matched,
        UsuarioResponse matchedWith) {

    public static SwipeResult noMatch() {
        return new SwipeResult(false, null);
    }

    public static SwipeResult match(UsuarioResponse matchedWith) {
        return new SwipeResult(true, matchedWith);
    }
}
