package com.pisomatch.service;

import com.pisomatch.domain.PerfilConvivencia;
import com.pisomatch.domain.PreferenciasBusqueda;
import org.springframework.stereotype.Component;

/**
 * Calcula la compatibilidad (0-100) de un candidato respecto a quien busca,
 * combinando las preferencias de búsqueda del buscador con el perfil de
 * convivencia del candidato. Es tolerante a datos ausentes: si falta información,
 * ese criterio simplemente no suma.
 */
@Component
public class CompatibilityCalculator {

    private static final int ZONA_WEIGHT = 40;
    private static final int PRESUPUESTO_WEIGHT = 40;
    private static final int CIUDAD_WEIGHT = 20;

    /**
     * @param prefsBuscador     preferencias de búsqueda de quien explora (puede ser null)
     * @param perfilBuscador    perfil de convivencia de quien explora (puede ser null)
     * @param perfilCandidato   perfil de convivencia del candidato (puede ser null)
     */
    public int score(PreferenciasBusqueda prefsBuscador,
                     PerfilConvivencia perfilBuscador,
                     PerfilConvivencia perfilCandidato) {
        if (perfilCandidato == null) {
            return 0;
        }
        int score = 0;

        // La zona deseada del buscador coincide con la ciudad del candidato.
        if (prefsBuscador != null
                && coincideZona(prefsBuscador.getZonaDeseada(), perfilCandidato.getCiudad())) {
            score += ZONA_WEIGHT;
        }

        // El presupuesto del candidato entra en el rango de búsqueda del buscador.
        if (prefsBuscador != null
                && perfilCandidato.getPresupuesto() >= prefsBuscador.getPresupuestoMinimo()
                && perfilCandidato.getPresupuesto() <= prefsBuscador.getPresupuestoMaximo()) {
            score += PRESUPUESTO_WEIGHT;
        }

        // Ambos buscan en la misma ciudad.
        if (perfilBuscador != null
                && perfilBuscador.getCiudad().equalsIgnoreCase(perfilCandidato.getCiudad())) {
            score += CIUDAD_WEIGHT;
        }

        return score;
    }

    private boolean coincideZona(String zonaDeseada, String ciudad) {
        if (zonaDeseada == null || ciudad == null) {
            return false;
        }
        String a = zonaDeseada.toLowerCase();
        String b = ciudad.toLowerCase();
        return a.contains(b) || b.contains(a);
    }
}
