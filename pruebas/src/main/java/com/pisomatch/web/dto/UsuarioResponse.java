package com.pisomatch.web.dto;

import com.pisomatch.domain.Usuario;

/**
 * Representación pública de un usuario.
 */
public record UsuarioResponse(
        Long id,
        String nombre,
        int edad,
        String foto,
        String profesion,
        String descripcion) {

    public static UsuarioResponse from(Usuario u) {
        return new UsuarioResponse(
                u.getId(), u.getNombre(), u.getEdad(), u.getFoto(),
                u.getProfesion(), u.getDescripcion());
    }
}
