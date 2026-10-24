package com.pisomatch.web.dto;

/**
 * Candidato del mazo de descubrimiento: el usuario, su perfil de convivencia
 * (si lo tiene) y el porcentaje de compatibilidad (0-100) respecto a quien busca.
 */
public record CandidateResponse(
        UsuarioResponse usuario,
        PerfilConvivenciaResponse perfil,
        int compatibilidad) {
}
