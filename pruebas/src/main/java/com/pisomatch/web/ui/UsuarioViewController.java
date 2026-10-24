package com.pisomatch.web.ui;

import com.pisomatch.domain.PerfilConvivencia;
import com.pisomatch.domain.PreferenciasBusqueda;
import com.pisomatch.domain.Usuario;
import com.pisomatch.service.MatchingService;
import com.pisomatch.service.PerfilConvivenciaService;
import com.pisomatch.service.PreferenciasBusquedaService;
import com.pisomatch.service.UsuarioService;
import com.pisomatch.service.ViviendaService;
import com.pisomatch.web.dto.CandidateResponse;
import com.pisomatch.web.dto.PerfilConvivenciaRequest;
import com.pisomatch.web.dto.PreferenciasBusquedaRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/ui/usuarios/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.getById(id);
        UsuarioForm form = new UsuarioForm();
        form.setNombre(usuario.getNombre());
        form.setEdad(usuario.getEdad());
        form.setFoto(usuario.getFoto());
        form.setProfesion(usuario.getProfesion());
        form.setDescripcion(usuario.getDescripcion());
        model.addAttribute("usuarioForm", form);
        model.addAttribute("usuarioId", id);
        model.addAttribute("usuario", usuario);
        return "usuarios/edit";
    }

    @PostMapping("/ui/usuarios/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("usuarioForm") UsuarioForm form,
                         BindingResult binding,
                         Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("usuarioId", id);
            model.addAttribute("usuario", usuarioService.getById(id));
            return "usuarios/edit";
        }
        usuarioService.update(id, form.toRequest());
        return "redirect:/ui/usuarios/" + id;
    }

    @GetMapping("/ui/usuarios/{id}/perfil/edit")
    public String editPerfilForm(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getById(id));
        PerfilConvivenciaRequest form = perfilService.findByUsuario(id)
                .map(p -> new PerfilConvivenciaRequest(p.getPresupuesto(), p.getCiudad(),
                        p.getFechaEntrada(), p.getHabitos(), p.getPreferencias()))
                .orElse(new PerfilConvivenciaRequest(500, "Madrid", null, "", ""));
        model.addAttribute("perfilForm", form);
        model.addAttribute("usuarioId", id);
        return "usuarios/perfil-edit";
    }

    @PostMapping("/ui/usuarios/{id}/perfil/edit")
    public String updatePerfil(@PathVariable Long id,
                               @Valid @ModelAttribute("perfilForm") PerfilConvivenciaRequest form,
                               BindingResult binding,
                               Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("usuario", usuarioService.getById(id));
            model.addAttribute("usuarioId", id);
            return "usuarios/perfil-edit";
        }
        perfilService.guardar(id, form);
        return "redirect:/ui/usuarios/" + id;
    }

    @GetMapping("/ui/usuarios/{id}/preferencias/edit")
    public String editPreferenciasForm(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getById(id));
        PreferenciasBusquedaRequest form = preferenciasService.findByUsuario(id)
                .map(p -> new PreferenciasBusquedaRequest(p.getPresupuestoMinimo(),
                        p.getPresupuestoMaximo(), p.getZonaDeseada(),
                        p.getAceptaMascotas(), p.getFumador(), p.getNivelRuido(),
                        p.getVisitas(), p.getLimpieza()))
                .orElse(new PreferenciasBusquedaRequest(400, 650, "Madrid"));
        model.addAttribute("preferenciasForm", form);
        model.addAttribute("usuarioId", id);
        return "usuarios/preferencias-edit";
    }

    @PostMapping("/ui/usuarios/{id}/preferencias/edit")
    public String updatePreferencias(@PathVariable Long id,
                                     @Valid @ModelAttribute("preferenciasForm") PreferenciasBusquedaRequest form,
                                     BindingResult binding,
                                     Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("usuario", usuarioService.getById(id));
            model.addAttribute("usuarioId", id);
            return "usuarios/preferencias-edit";
        }
        preferenciasService.guardar(id, form);
        return "redirect:/ui/usuarios/" + id;
    }

    @GetMapping("/ui/viviendas")
    public String viviendas(Model model) {
        model.addAttribute("viviendas", viviendaService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());
        return "viviendas/list";
    }

    @GetMapping("/ui/usuarios/{id}/viviendas")
    public String misViviendas(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("viviendas", viviendaService.findByPropietario(id));
        model.addAttribute("usuarioId", id);
        return "viviendas/mis-viviendas";
    }

    @GetMapping("/ui/usuarios/{id}/viviendas/new")
    public String nuevaVivienda(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("usuarioId", id);
        model.addAttribute("viviendaForm", new ViviendaForm());
        return "viviendas/form";
    }

    @PostMapping("/ui/usuarios/{id}/viviendas")
    public String crearVivienda(@PathVariable Long id,
                                @Valid @ModelAttribute("viviendaForm") ViviendaForm form,
                                BindingResult binding,
                                Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("usuario", usuarioService.getById(id));
            model.addAttribute("usuarioId", id);
            return "viviendas/form";
        }
        viviendaService.create(new com.pisomatch.web.dto.ViviendaRequest(id, form.getDireccion(),
                form.getZona(), form.getPrecio(), form.getHabitaciones(), form.getFotosLista()));
        return "redirect:/ui/usuarios/" + id + "/viviendas";
    }

    @GetMapping("/ui/usuarios/{id}/viviendas/{viviendaId}/edit")
    public String editarVivienda(@PathVariable Long id, @PathVariable Long viviendaId, Model model) {
        com.pisomatch.domain.Vivienda v = viviendaService.getById(viviendaId);
        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("usuarioId", id);
        model.addAttribute("viviendaId", viviendaId);
        ViviendaForm form = new ViviendaForm();
        form.setDireccion(v.getDireccion());
        form.setZona(v.getZona());
        form.setPrecio(v.getPrecio());
        form.setHabitaciones(v.getHabitaciones());
        form.setFotos(String.join("\n", v.getFotos() != null ? v.getFotos() : List.of()));
        model.addAttribute("viviendaForm", form);
        return "viviendas/form";
    }

    @PostMapping("/ui/usuarios/{id}/viviendas/{viviendaId}/edit")
    public String actualizarVivienda(@PathVariable Long id, @PathVariable Long viviendaId,
                                     @Valid @ModelAttribute("viviendaForm") ViviendaForm form,
                                     BindingResult binding,
                                     Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("usuario", usuarioService.getById(id));
            model.addAttribute("usuarioId", id);
            model.addAttribute("viviendaId", viviendaId);
            return "viviendas/form";
        }
        viviendaService.update(viviendaId, new com.pisomatch.web.dto.ViviendaRequest(id, form.getDireccion(),
                form.getZona(), form.getPrecio(), form.getHabitaciones(), form.getFotosLista()));
        return "redirect:/ui/usuarios/" + id + "/viviendas";
    }

    @PostMapping("/ui/usuarios/{id}/viviendas/{viviendaId}/delete")
    public String borrarVivienda(@PathVariable Long id, @PathVariable Long viviendaId) {
        viviendaService.delete(viviendaId, id);
        return "redirect:/ui/usuarios/" + id + "/viviendas";
    }

    @GetMapping("/ui/usuarios/{id}/intereses")
    public String misIntereses(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("viviendas", viviendaService.interesesDe(id));
        model.addAttribute("usuarioId", id);
        return "viviendas/intereses";
    }

    @GetMapping("/ui/usuarios/{id}/matches/viviendas")
    public String viviendasDeMisMatches(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.getById(id));
        model.addAttribute("usuarioId", id);
        List<Map<String, Object>> bloques = new ArrayList<>();
        for (com.pisomatch.web.dto.MatchResponse m : matchingService.matchesOf(id)) {
            Map<String, Object> bloque = new HashMap<>();
            bloque.put("match", m.con());
            bloque.put("viviendas", viviendaService.interesesDe(m.con().id()));
            bloques.add(bloque);
        }
        model.addAttribute("bloques", bloques);
        return "viviendas/matches-viviendas";
    }
}
