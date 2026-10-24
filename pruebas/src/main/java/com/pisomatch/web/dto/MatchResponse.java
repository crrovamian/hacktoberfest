package com.pisomatch.web.dto;

import com.pisomatch.domain.EstadoLike;

import java.time.Instant;

/**
 * Un match desde el punto de vista de un usuario: el otro usuario de la pareja,
 * el estado y cuándo se registró.
 */
public record MatchResponse(
        Long likeId,
        UsuarioResponse con,
        EstadoLike estado,
        Instant fecha) {
}
