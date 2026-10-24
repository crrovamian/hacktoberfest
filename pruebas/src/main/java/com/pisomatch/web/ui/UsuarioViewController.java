package com.pisomatch.web.ui;

import com.pisomatch.domain.Usuario;
import com.pisomatch.service.MatchingService;
import com.pisomatch.service.PerfilConvivenciaService;
import com.pisomatch.service.PreferenciasBusquedaService;
import com.pisomatch.service.UsuarioService;
import com.pisomatch.service.ViviendaService;
import com.pisomatch.web.dto.CandidateResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Vistas Thymeleaf: listado de usuarios, detalle (con perfil, preferencias,
 * viviendas, matches y candidatos), alta de usuario y listado de viviendas.
 */
@Controller
public class UsuarioViewController {

    private static final int TOP_CANDIDATES = 5;

    private final UsuarioService usuarioService;
    private final PerfilConvivenciaService perfilService;
    private final PreferenciasBusquedaService preferenciasService;
    private final ViviendaService viviendaService;
    private final MatchingService matchingService;

    public UsuarioViewController(UsuarioService usuarioService,
                                 PerfilConvivenciaService perfilService,
                                 PreferenciasBusquedaService preferenciasService,
                                 ViviendaService viviendaService,
                                 MatchingService matchingService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;
        this.preferenciasService = preferenciasService;
        this.viviendaService = viviendaService;
        this.matchingService = matchingService;
    }

    @GetMapping("/ui")
    public String home() {
        return "redirect:/ui/usuarios";
    }

    @GetMapping("/ui/usuarios")
    public String list(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios/list";
    }

    @GetMapping("/ui/usuarios/new")
    public String newForm(Model model) {
        model.addAttribute("usuarioForm", new UsuarioForm());
        return "usuarios/form";
    }

    @PostMapping("/ui/usuarios")
    public String create(@Valid @ModelAttribute("usuarioForm") UsuarioForm form,
                         BindingResult binding) {
        if (binding.hasErrors()) {
            return "usuarios/form";
        }
        Usuario creado = usuarioService.create(form.toRequest());
        return "redirect:/ui/usuarios/" + creado.getId();
    }

    @GetMapping("/ui/usuarios/{id}")
    public String detail(@PathVariable Long id, Model model) {
        List<CandidateResponse> candidatos = matchingService.discover(id);
        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("perfil", perfilService.findByUsuario(id).orElse(null));
        model.addAttribute("preferencias", preferenciasService.findByUsuario(id).orElse(null));
        model.addAttribute("viviendas", viviendaService.findByPropietario(id));
        model.addAttribute("matches", matchingService.matchesOf(id));
        model.addAttribute("candidatos",
                candidatos.subList(0, Math.min(TOP_CANDIDATES, candidatos.size())));
        return "usuarios/detail";
    }

    @GetMapping("/ui/viviendas")
    public String viviendas(Model model) {
        model.addAttribute("viviendas", viviendaService.findAll());
        return "viviendas/list";
    }
}
