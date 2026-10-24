package com.pisomatch.service;

import com.pisomatch.domain.Vivienda;
import com.pisomatch.repository.ViviendaRepository;
import com.pisomatch.web.dto.ViviendaRequest;
import com.pisomatch.web.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Gestión de viviendas publicadas por los usuarios.
 */
@Service
public class ViviendaService {

    private final ViviendaRepository viviendaRepository;
    private final UsuarioService usuarioService;

    public ViviendaService(ViviendaRepository viviendaRepository, UsuarioService usuarioService) {
        this.viviendaRepository = viviendaRepository;
        this.usuarioService = usuarioService;
    }

    public Vivienda create(ViviendaRequest req) {
        usuarioService.getById(req.propietarioId()); // valida que el propietario existe
        Vivienda vivienda = new Vivienda(req.propietarioId(), req.direccion(), req.zona(),
                req.precio(), req.habitaciones(), req.fotos());
        return viviendaRepository.save(vivienda);
    }

    public Vivienda getById(Long id) {
        return viviendaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la vivienda " + id));
    }

    public List<Vivienda> findAll() {
        return viviendaRepository.findAll();
    }

    public List<Vivienda> findByPropietario(Long propietarioId) {
        return viviendaRepository.findByPropietarioId(propietarioId);
    }
}
