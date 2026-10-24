package com.pisomatch.service;

import com.pisomatch.domain.Usuario;
import com.pisomatch.repository.UsuarioRepository;
import com.pisomatch.web.dto.UsuarioRequest;
import com.pisomatch.web.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Gestión de usuarios: alta, consulta y actualización de datos de identidad.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario create(UsuarioRequest req) {
        Usuario usuario = new Usuario(
                req.nombre(), req.edad(), req.foto(), req.profesion(), req.descripcion());
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, UsuarioRequest req) {
        Usuario usuario = getById(id);
        usuario.setNombre(req.nombre());
        usuario.setEdad(req.edad());
        usuario.setFoto(req.foto());
        usuario.setProfesion(req.profesion());
        usuario.setDescripcion(req.descripcion());
        return usuarioRepository.save(usuario);
    }

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe el usuario " + id));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
