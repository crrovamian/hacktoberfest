package com.pisomatch.service;

import com.pisomatch.domain.PreferenciasBusqueda;
import com.pisomatch.repository.PreferenciasBusquedaRepository;
import com.pisomatch.web.dto.PreferenciasBusquedaRequest;
import com.pisomatch.web.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Preferencias de búsqueda de un usuario (upsert: una por usuario).
 */
@Service
public class PreferenciasBusquedaService {

    private final PreferenciasBusquedaRepository repository;
    private final UsuarioService usuarioService;

    public PreferenciasBusquedaService(PreferenciasBusquedaRepository repository,
                                       UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public PreferenciasBusqueda guardar(Long usuarioId, PreferenciasBusquedaRequest req) {
        if (req.presupuestoMinimo() > req.presupuestoMaximo()) {
            throw new IllegalArgumentException(
                    "El presupuesto mínimo no puede ser mayor que el máximo");
        }
        usuarioService.getById(usuarioId); // valida que el usuario existe
        PreferenciasBusqueda prefs = repository.findByUsuarioId(usuarioId)
                .orElseGet(() -> new PreferenciasBusqueda(usuarioId, req.presupuestoMinimo(),
                        req.presupuestoMaximo(), req.zonaDeseada(),
                        req.aceptaMascotas(), req.fumador(), req.nivelRuido(), req.visitas(), req.limpieza()));
        prefs.setPresupuestoMinimo(req.presupuestoMinimo());
        prefs.setPresupuestoMaximo(req.presupuestoMaximo());
        prefs.setZonaDeseada(req.zonaDeseada());
        prefs.setAceptaMascotas(req.aceptaMascotas());
        prefs.setFumador(req.fumador());
        prefs.setNivelRuido(req.nivelRuido());
        prefs.setVisitas(req.visitas());
        prefs.setLimpieza(req.limpieza());
        return repository.save(prefs);
    }

    public PreferenciasBusqueda getByUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new NotFoundException(
                        "El usuario " + usuarioId + " no tiene preferencias de búsqueda"));
    }

    public Optional<PreferenciasBusqueda> findByUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
