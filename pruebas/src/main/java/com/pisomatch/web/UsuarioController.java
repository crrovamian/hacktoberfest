package com.pisomatch.web;

import com.pisomatch.service.PerfilConvivenciaService;
import com.pisomatch.service.PreferenciasBusquedaService;
import com.pisomatch.service.UsuarioService;
import com.pisomatch.web.dto.PerfilConvivenciaRequest;
import com.pisomatch.web.dto.PerfilConvivenciaResponse;
import com.pisomatch.web.dto.PreferenciasBusquedaRequest;
import com.pisomatch.web.dto.PreferenciasBusquedaResponse;
import com.pisomatch.web.dto.UsuarioRequest;
import com.pisomatch.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Alta y consulta de usuarios, junto con su perfil de convivencia y sus
 * preferencias de búsqueda.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PerfilConvivenciaService perfilService;
    private final PreferenciasBusquedaService preferenciasService;

    public UsuarioController(UsuarioService usuarioService,
                            PerfilConvivenciaService perfilService,
                            PreferenciasBusquedaService preferenciasService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
        this.preferenciasService = preferenciasService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse create(@Valid @RequestBody UsuarioRequest request) {
        return UsuarioResponse.from(usuarioService.create(request));
    }

    @GetMapping
    public List<UsuarioResponse> findAll() {
        return usuarioService.findAll().stream().map(UsuarioResponse::from).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse getById(@PathVariable Long id) {
        return UsuarioResponse.from(usuarioService.getById(id));
    }

    @PutMapping("/{id}")
    public UsuarioResponse update(@PathVariable Long id, @Valid @RequestBody UsuarioRequest request) {
        return UsuarioResponse.from(usuarioService.update(id, request));
    }

    // --- Perfil de convivencia ---

    @PutMapping("/{id}/perfil")
    public PerfilConvivenciaResponse guardarPerfil(
            @PathVariable Long id, @Valid @RequestBody PerfilConvivenciaRequest request) {
        return PerfilConvivenciaResponse.from(perfilService.guardar(id, request));
    }

    @GetMapping("/{id}/perfil")
    public PerfilConvivenciaResponse getPerfil(@PathVariable Long id) {
        return PerfilConvivenciaResponse.from(perfilService.getByUsuario(id));
    }

    // --- Preferencias de búsqueda ---

    @PutMapping("/{id}/preferencias")
    public PreferenciasBusquedaResponse guardarPreferencias(
            @PathVariable Long id, @Valid @RequestBody PreferenciasBusquedaRequest request) {
        return PreferenciasBusquedaResponse.from(preferenciasService.guardar(id, request));
    }

    @GetMapping("/{id}/preferencias")
    public PreferenciasBusquedaResponse getPreferencias(@PathVariable Long id) {
        return PreferenciasBusquedaResponse.from(preferenciasService.getByUsuario(id));
    }
}
