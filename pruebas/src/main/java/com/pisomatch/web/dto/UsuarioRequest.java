package com.pisomatch.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos de identidad para crear o actualizar un usuario.
 */
public record UsuarioRequest(
        @NotBlank String nombre,
        @Min(18) @Max(120) int edad,
        String foto,
        String profesion,
        @Size(max = 500) String descripcion) {
}
