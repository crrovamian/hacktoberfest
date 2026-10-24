package com.pisomatch.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

/**
 * Datos para publicar una vivienda.
 */
public record ViviendaRequest(
        @NotNull Long propietarioId,
        @NotBlank String direccion,
        @NotBlank String zona,
        @Positive int precio,
        @PositiveOrZero int habitaciones,
        List<String> fotos) {
}
