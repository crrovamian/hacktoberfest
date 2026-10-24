package com.pisomatch.web;

import com.pisomatch.service.MensajeService;
import com.pisomatch.web.dto.MensajeRequest;
import com.pisomatch.web.dto.MensajeResponse;
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
 * Chat entre usuarios con match.
 */
@RestController
@RequestMapping("/api/v1/usuarios/{id}/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    /** Envía un mensaje desde el usuario {id} al receptor indicado en el cuerpo. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MensajeResponse enviar(@PathVariable Long id, @Valid @RequestBody MensajeRequest request) {
        return MensajeResponse.from(
                mensajeService.enviar(id, request.receptorId(), request.contenido()));
    }

    /** Conversación entre el usuario {id} y {otroId}, ordenada cronológicamente. */
    @GetMapping("/{otroId}")
    public List<MensajeResponse> conversacion(@PathVariable Long id, @PathVariable Long otroId) {
        return mensajeService.conversacion(id, otroId).stream()
                .map(MensajeResponse::from)
                .toList();
    }

    /** Número total de mensajes sin leer para el usuario {id}. */
    @GetMapping("/sin-leer")
    public SinLeerResponse sinLeer(@PathVariable Long id) {
        return new SinLeerResponse(mensajeService.sinLeer(id));
    }

    /** Número de mensajes sin leer desde {emisorId}. */
    @GetMapping("/sin-leer/{emisorId}")
    public SinLeerResponse sinLeerDe(@PathVariable Long id, @PathVariable Long emisorId) {
        return new SinLeerResponse(mensajeService.sinLeer(id, emisorId));
    }

    public record SinLeerResponse(long total) {}
}
