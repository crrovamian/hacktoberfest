package com.pisomatch.web.dto;

import com.pisomatch.domain.Vivienda;

import java.util.List;

/**
 * Representación pública de una vivienda.
 */
public record ViviendaResponse(
        Long id,
        Long propietarioId,
        String direccion,
        String zona,
        int precio,
        int habitaciones,
        List<String> fotos) {

    public static ViviendaResponse from(Vivienda v) {
        return new ViviendaResponse(
                v.getId(), v.getPropietarioId(), v.getDireccion(), v.getZona(),
                v.getPrecio(), v.getHabitaciones(), List.copyOf(v.getFotos()));
    }
}
