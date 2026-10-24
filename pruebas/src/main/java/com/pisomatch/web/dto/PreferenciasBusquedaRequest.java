package com.pisomatch.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Criterios de búsqueda de compañero/a de piso.
 */
public record PreferenciasBusquedaRequest(
        @Positive int presupuestoMinimo,
        @Positive int presupuestoMaximo,
        @NotBlank String zonaDeseada) {
}
