package com.pisomatch.web;

import com.pisomatch.service.ViviendaService;
import com.pisomatch.web.dto.ViviendaRequest;
import com.pisomatch.web.dto.ViviendaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Publicación y consulta de viviendas.
 */
@RestController
@RequestMapping("/api/v1/viviendas")
public class ViviendaController {

    private final ViviendaService viviendaService;

    public ViviendaController(ViviendaService viviendaService) {
        this.viviendaService = viviendaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViviendaResponse create(@Valid @RequestBody ViviendaRequest request) {
        return ViviendaResponse.from(viviendaService.create(request));
    }

    @GetMapping
    public List<ViviendaResponse> findAll(@RequestParam(required = false) Long propietarioId) {
        List<com.pisomatch.domain.Vivienda> viviendas = (propietarioId != null)
                ? viviendaService.findByPropietario(propietarioId)
                : viviendaService.findAll();
        return viviendas.stream().map(ViviendaResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ViviendaResponse getById(@PathVariable Long id) {
        return ViviendaResponse.from(viviendaService.getById(id));
    }
}
