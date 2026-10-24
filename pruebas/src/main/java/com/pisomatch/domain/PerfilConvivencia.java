package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Preferencias de convivencia de un usuario (relación 1:1 con {@link Usuario}).
 * Describe presupuesto, ciudad, cuándo puede entrar y sus hábitos/preferencias.
 */
@Entity
@Table(name = "perfiles_convivencia")
public class PerfilConvivencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private Long usuarioId;

    /** Presupuesto mensual que está dispuesto/a a pagar, en euros. */
    @Column(nullable = false)
    private int presupuesto;

    @Column(nullable = false)
    private String ciudad;

    private LocalDate fechaEntrada;

    @Column(length = 1000)
    private String habitos;

    @Column(length = 1000)
    private String preferencias;

    protected PerfilConvivencia() {
        // Requerido por JPA.
    }

    public PerfilConvivencia(Long usuarioId, int presupuesto, String ciudad,
                             LocalDate fechaEntrada, String habitos, String preferencias) {
        this.usuarioId = usuarioId;
        this.presupuesto = presupuesto;
        this.ciudad = ciudad;
        this.fechaEntrada = fechaEntrada;
        this.habitos = habitos;
        this.preferencias = preferencias;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getHabitos() {
        return habitos;
    }

    public void setHabitos(String habitos) {
        this.habitos = habitos;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
}
