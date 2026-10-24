package com.pisomatch.service;

import com.pisomatch.domain.Mensaje;
import com.pisomatch.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Chat entre usuarios. Solo se puede enviar y leer la conversación si ambos
 * usuarios han hecho match.
 */
@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final UsuarioService usuarioService;
    private final MatchingService matchingService;

    public MensajeService(MensajeRepository mensajeRepository,
                          UsuarioService usuarioService,
                          MatchingService matchingService) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioService = usuarioService;
        this.matchingService = matchingService;
    }

    @Transactional
    public Mensaje enviar(Long emisorId, Long receptorId, String contenido) {
        usuarioService.getById(emisorId);
        usuarioService.getById(receptorId);
        if (!matchingService.hayMatch(emisorId, receptorId)) {
            throw new IllegalArgumentException(
                    "Solo puedes escribir a usuarios con los que tienes match");
        }
        return mensajeRepository.save(new Mensaje(emisorId, receptorId, contenido));
    }

    @Transactional(readOnly = true)
    public List<Mensaje> conversacion(Long usuarioId, Long otroId) {
        usuarioService.getById(usuarioId);
        usuarioService.getById(otroId);
        List<Long> ambos = List.of(usuarioId, otroId);
        return mensajeRepository.findByEmisorIdInAndReceptorIdInOrderByFechaAsc(ambos, ambos);
    }
}
