package com.pisomatch.web;

import com.pisomatch.service.MatchingService;
import com.pisomatch.service.ViviendaService;
import com.pisomatch.web.dto.ViviendaRequest;
import com.pisomatch.web.dto.ViviendaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final MatchingService matchingService;

    public ViviendaController(ViviendaService viviendaService, MatchingService matchingService) {
        this.viviendaService = viviendaService;
        this.matchingService = matchingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViviendaResponse create(@Valid @RequestBody ViviendaRequest request) {
        return ViviendaResponse.from(viviendaService.create(request));
    }

    @PutMapping("/{id}")
    public ViviendaResponse update(@PathVariable Long id, @Valid @RequestBody ViviendaRequest request) {
        return ViviendaResponse.from(viviendaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @RequestParam Long propietarioId) {
        viviendaService.delete(id, propietarioId);
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

    @PostMapping("/{id}/interes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarInteres(@PathVariable Long id, @RequestParam Long usuarioId) {
        viviendaService.marcarInteres(usuarioId, id);
    }

    @DeleteMapping("/{id}/interes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void quitarInteres(@PathVariable Long id, @RequestParam Long usuarioId) {
        viviendaService.quitarInteres(usuarioId, id);
    }

    @GetMapping("/intereses")
    public List<ViviendaResponse> misIntereses(@RequestParam Long usuarioId) {
        return viviendaService.interesesDe(usuarioId).stream().map(ViviendaResponse::from).toList();
    }

    @GetMapping("/intereses/match")
    public List<ViviendaResponse> interesesDeMatch(@RequestParam Long usuarioId, @RequestParam Long matchId) {
        if (!matchingService.hayMatch(usuarioId, matchId)) {
            throw new IllegalArgumentException("Solo puedes ver los pisos de un usuario con el que tienes match");
        }
        return viviendaService.interesesDe(matchId).stream().map(ViviendaResponse::from).toList();
    }

    @GetMapping("/intereses/ids")
    public List<Long> misInteresesIds(@RequestParam Long usuarioId) {
        return viviendaService.interesesIds(usuarioId);
    }
}
