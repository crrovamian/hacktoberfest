package com.pisomatch.web;

import com.pisomatch.service.MatchingService;
import com.pisomatch.web.dto.CandidateResponse;
import com.pisomatch.web.dto.MatchResponse;
import com.pisomatch.web.dto.SwipeRequest;
import com.pisomatch.web.dto.SwipeResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Descubrimiento de candidatos, likes/pass y matches de un usuario.
 */
@RestController
@RequestMapping("/api/v1/usuarios/{id}")
public class MatchingController {

    private final MatchingService matchingService;

    public MatchingController(MatchingService matchingService) {
        this.matchingService = matchingService;
    }

    /** Mazo de candidatos ordenado por compatibilidad. */
    @GetMapping("/discover")
    public List<CandidateResponse> discover(@PathVariable Long id) {
        return matchingService.discover(id);
    }

    /** Registra un like o pass sobre un candidato; indica si hay match. */
    @PostMapping("/swipes")
    public SwipeResult swipe(@PathVariable Long id, @Valid @RequestBody SwipeRequest request) {
        return matchingService.swipe(id, request.objetivoId(), request.liked());
    }

    /** Lista los matches del usuario. */
    @GetMapping("/matches")
    public List<MatchResponse> matches(@PathVariable Long id) {
        return matchingService.matchesOf(id);
    }
}
