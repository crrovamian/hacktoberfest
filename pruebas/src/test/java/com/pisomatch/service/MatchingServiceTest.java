package com.pisomatch.service;

import com.pisomatch.domain.Usuario;
import com.pisomatch.web.dto.CandidateResponse;
import com.pisomatch.web.dto.MatchResponse;
import com.pisomatch.web.dto.SwipeResult;
import com.pisomatch.web.dto.UsuarioRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MatchingServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private MensajeService mensajeService;

    private Usuario nuevoUsuario(String nombre) {
        return usuarioService.create(new UsuarioRequest(
                nombre + "-" + System.nanoTime(), 28, null, "profesión", "descripción"));
    }

    @Test
    void likeMutuoCreaMatch() {
        Usuario ana = nuevoUsuario("Ana");
        Usuario ben = nuevoUsuario("Ben");

        SwipeResult primero = matchingService.swipe(ana.getId(), ben.getId(), true);
        assertThat(primero.matched()).isFalse();

        SwipeResult segundo = matchingService.swipe(ben.getId(), ana.getId(), true);
        assertThat(segundo.matched()).isTrue();
        assertThat(segundo.matchedWith().id()).isEqualTo(ana.getId());

        List<MatchResponse> matchesDeAna = matchingService.matchesOf(ana.getId());
        assertThat(matchesDeAna).extracting(m -> m.con().id()).contains(ben.getId());
        assertThat(matchingService.hayMatch(ana.getId(), ben.getId())).isTrue();
    }

    @Test
    void passNoCreaMatch() {
        Usuario ana = nuevoUsuario("Ana");
        Usuario ben = nuevoUsuario("Ben");

        matchingService.swipe(ana.getId(), ben.getId(), true);
        SwipeResult resultado = matchingService.swipe(ben.getId(), ana.getId(), false);

        assertThat(resultado.matched()).isFalse();
        assertThat(matchingService.matchesOf(ana.getId())).isEmpty();
    }

    @Test
    void noSePuedeValorarDosVecesAlMismoUsuario() {
        Usuario ana = nuevoUsuario("Ana");
        Usuario ben = nuevoUsuario("Ben");

        matchingService.swipe(ana.getId(), ben.getId(), true);

        assertThatThrownBy(() -> matchingService.swipe(ana.getId(), ben.getId(), false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void descubrirExcluyePropioUsuarioYLosYaValorados() {
        Usuario ana = nuevoUsuario("Ana");
        Usuario ben = nuevoUsuario("Ben");

        List<CandidateResponse> antes = matchingService.discover(ana.getId());
        assertThat(antes).extracting(c -> c.usuario().id()).contains(ben.getId());
        assertThat(antes).extracting(c -> c.usuario().id()).doesNotContain(ana.getId());

        matchingService.swipe(ana.getId(), ben.getId(), false);

        List<CandidateResponse> despues = matchingService.discover(ana.getId());
        assertThat(despues).extracting(c -> c.usuario().id()).doesNotContain(ben.getId());
    }

    @Test
    void soloSePuedeChatearConMatch() {
        Usuario ana = nuevoUsuario("Ana");
        Usuario ben = nuevoUsuario("Ben");

        // Sin match, enviar mensaje falla.
        assertThatThrownBy(() -> mensajeService.enviar(ana.getId(), ben.getId(), "hola"))
                .isInstanceOf(IllegalArgumentException.class);

        // Creamos el match recíproco.
        matchingService.swipe(ana.getId(), ben.getId(), true);
        matchingService.swipe(ben.getId(), ana.getId(), true);

        mensajeService.enviar(ana.getId(), ben.getId(), "¡hola Ben!");
        mensajeService.enviar(ben.getId(), ana.getId(), "¡hola Ana!");

        assertThat(mensajeService.conversacion(ana.getId(), ben.getId()))
                .hasSize(2)
                .extracting(m -> m.getContenido())
                .containsExactly("¡hola Ben!", "¡hola Ana!");
    }
}
