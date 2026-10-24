package com.pisomatch.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Datos del perfil de convivencia de un usuario.
 */
public record PerfilConvivenciaRequest(
        @Positive int presupuesto,
        @NotBlank String ciudad,
        LocalDate fechaEntrada,
        @Size(max = 1000) String habitos,
        @Size(max = 1000) String preferencias) {
}
