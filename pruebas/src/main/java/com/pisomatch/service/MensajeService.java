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

    @Transactional
    public List<Mensaje> conversacion(Long usuarioId, Long otroId) {
        usuarioService.getById(usuarioId);
        usuarioService.getById(otroId);
        List<Mensaje> mensajes = mensajeRepository.findByEmisorIdInAndReceptorIdInOrderByFechaAsc(
                List.of(usuarioId, otroId), List.of(usuarioId, otroId));
        List<Mensaje> pendientes = mensajeRepository.findByReceptorIdAndEmisorIdAndLeidoFalse(usuarioId, otroId);
        if (!pendientes.isEmpty()) {
            pendientes.forEach(m -> m.setLeido(true));
            mensajeRepository.saveAll(pendientes);
        }
        return mensajes;
    }

    @Transactional(readOnly = true)
    public long sinLeer(Long receptorId) {
        usuarioService.getById(receptorId);
        return mensajeRepository.countByReceptorIdAndLeidoFalse(receptorId);
    }

    @Transactional(readOnly = true)
    public long sinLeer(Long receptorId, Long emisorId) {
        usuarioService.getById(receptorId);
        usuarioService.getById(emisorId);
        return mensajeRepository.countByReceptorIdAndEmisorIdAndLeidoFalse(receptorId, emisorId);
    }
}
