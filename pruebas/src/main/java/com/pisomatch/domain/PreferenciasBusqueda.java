package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Criterios con los que un usuario busca compañero/a de piso (relación 1:1 con
 * {@link Usuario}): rango de presupuesto y zona deseada.
 */
@Entity
@Table(name = "preferencias_busqueda")
public class PreferenciasBusqueda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private Long usuarioId;

    @Column(nullable = false)
    private int presupuestoMinimo;

    @Column(nullable = false)
    private int presupuestoMaximo;

    @Column(nullable = false)
    private String zonaDeseada;

    protected PreferenciasBusqueda() {
        // Requerido por JPA.
    }

    public PreferenciasBusqueda(Long usuarioId, int presupuestoMinimo,
                                int presupuestoMaximo, String zonaDeseada) {
        this.usuarioId = usuarioId;
        this.presupuestoMinimo = presupuestoMinimo;
        this.presupuestoMaximo = presupuestoMaximo;
        this.zonaDeseada = zonaDeseada;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public int getPresupuestoMinimo() {
        return presupuestoMinimo;
    }

    public void setPresupuestoMinimo(int presupuestoMinimo) {
        this.presupuestoMinimo = presupuestoMinimo;
    }

    public int getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public void setPresupuestoMaximo(int presupuestoMaximo) {
        this.presupuestoMaximo = presupuestoMaximo;
    }

    public String getZonaDeseada() {
        return zonaDeseada;
    }

    public void setZonaDeseada(String zonaDeseada) {
        this.zonaDeseada = zonaDeseada;
    }
}
