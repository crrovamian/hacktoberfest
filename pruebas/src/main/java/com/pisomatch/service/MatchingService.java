package com.pisomatch.service;

import com.pisomatch.domain.EstadoLike;
import com.pisomatch.domain.Like;
import com.pisomatch.domain.PerfilConvivencia;
import com.pisomatch.domain.PreferenciasBusqueda;
import com.pisomatch.domain.Usuario;
import com.pisomatch.repository.LikeRepository;
import com.pisomatch.web.dto.CandidateResponse;
import com.pisomatch.web.dto.MatchResponse;
import com.pisomatch.web.dto.PerfilConvivenciaResponse;
import com.pisomatch.web.dto.SwipeResult;
import com.pisomatch.web.dto.UsuarioResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Lógica del "Tinder de pisos": mazo de descubrimiento, registro de likes/pass
 * y detección de matches recíprocos.
 */
@Service
public class MatchingService {

    private final UsuarioService usuarioService;
    private final PerfilConvivenciaService perfilService;
    private final PreferenciasBusquedaService preferenciasService;
    private final LikeRepository likeRepository;
    private final CompatibilityCalculator compatibilidad;

    public MatchingService(UsuarioService usuarioService,
                           PerfilConvivenciaService perfilService,
                           PreferenciasBusquedaService preferenciasService,
                           LikeRepository likeRepository,
                           CompatibilityCalculator compatibilidad) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
        this.preferenciasService = preferenciasService;
        this.likeRepository = likeRepository;
        this.compatibilidad = compatibilidad;
    }

    /** Candidatos aún no valorados por el usuario, ordenados por compatibilidad. */
    @Transactional(readOnly = true)
    public List<CandidateResponse> discover(Long usuarioId) {
        usuarioService.getById(usuarioId);

        PreferenciasBusqueda misPrefs = preferenciasService.findByUsuario(usuarioId).orElse(null);
        PerfilConvivencia miPerfil = perfilService.findByUsuario(usuarioId).orElse(null);

        Set<Long> yaValorados = likeRepository.findByUsuarioId(usuarioId).stream()
                .map(Like::getUsuarioObjetivoId)
                .collect(Collectors.toSet());

        return usuarioService.findAll().stream()
                .filter(u -> !u.getId().equals(usuarioId))
                .filter(u -> !yaValorados.contains(u.getId()))
                .map(u -> {
                    PerfilConvivencia perfil = perfilService.findByUsuario(u.getId()).orElse(null);
                    int score = compatibilidad.score(misPrefs, miPerfil, perfil);
                    PerfilConvivenciaResponse perfilDto =
                            perfil != null ? PerfilConvivenciaResponse.from(perfil) : null;
                    return new CandidateResponse(UsuarioResponse.from(u), perfilDto, score);
                })
                .sorted(Comparator.comparingInt(CandidateResponse::compatibilidad).reversed()
                        .thenComparing(c -> c.usuario().id()))
                .toList();
    }

    /**
     * Registra el like/pass del usuario sobre otro. Si el otro ya le había dado like,
     * ambas filas pasan a MATCH y se devuelve el usuario con el que ha hecho match.
     */
    @Transactional
    public SwipeResult swipe(Long usuarioId, Long objetivoId, boolean liked) {
        if (usuarioId.equals(objetivoId)) {
            throw new IllegalArgumentException("No puedes valorarte a ti mismo");
        }
        usuarioService.getById(usuarioId);
        Usuario objetivo = usuarioService.getById(objetivoId);

        likeRepository.findByUsuarioIdAndUsuarioObjetivoId(usuarioId, objetivoId)
                .ifPresent(l -> {
                    throw new IllegalArgumentException("Ya has valorado al usuario " + objetivoId);
                });

        Like like = likeRepository.save(
                new Like(usuarioId, objetivoId, liked ? EstadoLike.LIKE : EstadoLike.PASS));

        if (liked && likeReciproco(objetivoId, usuarioId)) {
            like.setEstado(EstadoLike.MATCH);
            Like reciproco =
                    likeRepository.findByUsuarioIdAndUsuarioObjetivoId(objetivoId, usuarioId).get();
            reciproco.setEstado(EstadoLike.MATCH);
            likeRepository.save(like);
            likeRepository.save(reciproco);
            return SwipeResult.match(UsuarioResponse.from(objetivo));
        }
        return SwipeResult.noMatch();
    }

    @Transactional(readOnly = true)
    public List<MatchResponse> matchesOf(Long usuarioId) {
        usuarioService.getById(usuarioId);

        return likeRepository.findByUsuarioIdAndEstado(usuarioId, EstadoLike.MATCH).stream()
                .map(like -> {
                    Usuario otro = usuarioService.getById(like.getUsuarioObjetivoId());
                    return new MatchResponse(
                            like.getId(), UsuarioResponse.from(otro), like.getEstado(), like.getFecha());
                })
                .sorted(Comparator.comparing(MatchResponse::fecha).reversed())
                .toList();
    }

    /** ¿Existe un match (o like pendiente) entre ambos usuarios? */
    @Transactional(readOnly = true)
    public boolean hayMatch(Long a, Long b) {
        return likeRepository.findByUsuarioIdAndUsuarioObjetivoId(a, b)
                .map(l -> l.getEstado() == EstadoLike.MATCH)
                .orElse(false);
    }

    private boolean likeReciproco(Long objetivoId, Long usuarioId) {
        return likeRepository.findByUsuarioIdAndUsuarioObjetivoId(objetivoId, usuarioId)
                .map(l -> l.getEstado() == EstadoLike.LIKE || l.getEstado() == EstadoLike.MATCH)
                .orElse(false);
    }
}
