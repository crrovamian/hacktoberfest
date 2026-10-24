package com.pisomatch.service;

import com.pisomatch.domain.Vivienda;
import com.pisomatch.domain.ViviendaInteres;
import com.pisomatch.repository.ViviendaInteresRepository;
import com.pisomatch.repository.ViviendaRepository;
import com.pisomatch.web.dto.ViviendaRequest;
import com.pisomatch.web.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gestión de viviendas publicadas por los usuarios.
 */
@Service
public class ViviendaService {

    private final ViviendaRepository viviendaRepository;
    private final ViviendaInteresRepository viviendaInteresRepository;
    private final UsuarioService usuarioService;

    public ViviendaService(ViviendaRepository viviendaRepository,
                           ViviendaInteresRepository viviendaInteresRepository,
                           UsuarioService usuarioService) {
        this.viviendaRepository = viviendaRepository;
        this.viviendaInteresRepository = viviendaInteresRepository;
        this.usuarioService = usuarioService;
    }

    public Vivienda create(ViviendaRequest req) {
        usuarioService.getById(req.propietarioId()); // valida que el propietario existe
        Vivienda vivienda = new Vivienda(req.propietarioId(), req.direccion(), req.zona(),
                req.precio(), req.habitaciones(), req.fotos());
        return viviendaRepository.save(vivienda);
    }

    public Vivienda update(Long id, ViviendaRequest req) {
        Vivienda vivienda = getById(id);
        if (!vivienda.getPropietarioId().equals(req.propietarioId())) {
            throw new IllegalArgumentException("Solo el propietario puede modificar la vivienda");
        }
        vivienda.setDireccion(req.direccion());
        vivienda.setZona(req.zona());
        vivienda.setPrecio(req.precio());
        vivienda.setHabitaciones(req.habitaciones());
        vivienda.setFotos(req.fotos());
        return viviendaRepository.save(vivienda);
    }

    @Transactional
    public void delete(Long id, Long propietarioId) {
        Vivienda vivienda = getById(id);
        if (!vivienda.getPropietarioId().equals(propietarioId)) {
            throw new IllegalArgumentException("Solo el propietario puede eliminar la vivienda");
        }
        viviendaInteresRepository.deleteAll(
                viviendaInteresRepository.findByViviendaId(id));
        viviendaRepository.delete(vivienda);
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

    @Transactional
    public boolean marcarInteres(Long usuarioId, Long viviendaId) {
        usuarioService.getById(usuarioId);
        getById(viviendaId);
        if (viviendaInteresRepository.existsByUsuarioIdAndViviendaId(usuarioId, viviendaId)) {
            return false;
        }
        viviendaInteresRepository.save(new ViviendaInteres(usuarioId, viviendaId));
        return true;
    }

    @Transactional
    public boolean quitarInteres(Long usuarioId, Long viviendaId) {
        if (viviendaInteresRepository.findByUsuarioIdAndViviendaId(usuarioId, viviendaId).isPresent()) {
            viviendaInteresRepository.deleteByUsuarioIdAndViviendaId(usuarioId, viviendaId);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Vivienda> interesesDe(Long usuarioId) {
        usuarioService.getById(usuarioId);
        Set<Long> ids = viviendaInteresRepository.findByUsuarioId(usuarioId).stream()
                .map(ViviendaInteres::getViviendaId)
                .collect(Collectors.toSet());
        if (ids.isEmpty()) return List.of();
        return viviendaRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public List<Long> interesesIds(Long usuarioId) {
        return viviendaInteresRepository.findByUsuarioId(usuarioId).stream()
                .map(ViviendaInteres::getViviendaId)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Vivienda> interesesDeUsuarioParaMatch(Long propietarioDelMatch, Long usuarioQuePregunta) {
        // Si hay match entre propietarioDelMatch y usuarioQuePregunta, mostrar intereses del propietarioDelMatch
        return interesesDe(propietarioDelMatch);
    }
}

