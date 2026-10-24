package com.pisomatch.web.dto;

import com.pisomatch.domain.PreferenciasBusqueda;

/**
 * Representación pública de las preferencias de búsqueda.
 */
public record PreferenciasBusquedaResponse(
        Long id,
        Long usuarioId,
        int presupuestoMinimo,
        int presupuestoMaximo,
        String zonaDeseada) {

    public static PreferenciasBusquedaResponse from(PreferenciasBusqueda p) {
        return new PreferenciasBusquedaResponse(
                p.getId(), p.getUsuarioId(), p.getPresupuestoMinimo(),
                p.getPresupuestoMaximo(), p.getZonaDeseada());
    }
}
