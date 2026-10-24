package com.pisomatch.web.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Decisión sobre un candidato: a quién (usuario objetivo) y si es like o pass.
 */
public record SwipeRequest(
        @NotNull Long objetivoId,
        boolean liked) {
}
