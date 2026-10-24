package com.pisomatch.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Criterios de búsqueda de compañero/a de piso.
 */
public record PreferenciasBusquedaRequest(
        @Positive int presupuestoMinimo,
        @Positive int presupuestoMaximo,
        @NotBlank String zonaDeseada,
        String aceptaMascotas,
        String fumador,
        String nivelRuido,
        String visitas,
        String limpieza) {

    public PreferenciasBusquedaRequest(int presupuestoMinimo, int presupuestoMaximo, String zonaDeseada) {
        this(presupuestoMinimo, presupuestoMaximo, zonaDeseada, "Sin preferencia", "Sin preferencia", "Sin preferencia", "Sin preferencia", "Sin preferencia");
    }
}
