package com.pisomatch.web.dto;

import com.pisomatch.domain.PerfilConvivencia;

import java.time.LocalDate;

/**
 * Representación pública del perfil de convivencia.
 */
public record PerfilConvivenciaResponse(
        Long id,
        Long usuarioId,
        int presupuesto,
        String ciudad,
        LocalDate fechaEntrada,
        String habitos,
        String preferencias) {

    public static PerfilConvivenciaResponse from(PerfilConvivencia p) {
        return new PerfilConvivenciaResponse(
                p.getId(), p.getUsuarioId(), p.getPresupuesto(), p.getCiudad(),
                p.getFechaEntrada(), p.getHabitos(), p.getPreferencias());
    }
}
