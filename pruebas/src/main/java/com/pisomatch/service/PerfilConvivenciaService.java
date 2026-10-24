package com.pisomatch.service;

import com.pisomatch.domain.PerfilConvivencia;
import com.pisomatch.repository.PerfilConvivenciaRepository;
import com.pisomatch.web.dto.PerfilConvivenciaRequest;
import com.pisomatch.web.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Perfil de convivencia de un usuario. Se crea o actualiza con una sola operación
 * (upsert), ya que cada usuario tiene como mucho un perfil.
 */
@Service
public class PerfilConvivenciaService {

    private final PerfilConvivenciaRepository repository;
    private final UsuarioService usuarioService;

    public PerfilConvivenciaService(PerfilConvivenciaRepository repository,
                                    UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public PerfilConvivencia guardar(Long usuarioId, PerfilConvivenciaRequest req) {
        usuarioService.getById(usuarioId); // valida que el usuario existe
        PerfilConvivencia perfil = repository.findByUsuarioId(usuarioId)
                .orElseGet(() -> new PerfilConvivencia(usuarioId, req.presupuesto(),
                        req.ciudad(), req.fechaEntrada(), req.habitos(), req.preferencias()));
        perfil.setPresupuesto(req.presupuesto());
        perfil.setCiudad(req.ciudad());
        perfil.setFechaEntrada(req.fechaEntrada());
        perfil.setHabitos(req.habitos());
        perfil.setPreferencias(req.preferencias());
        return repository.save(perfil);
    }

    public PerfilConvivencia getByUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NotFoundException(
                        "El usuario " + usuarioId + " no tiene perfil de convivencia"));
    }

    public Optional<PerfilConvivencia> findByUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
