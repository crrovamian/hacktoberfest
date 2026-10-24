package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Criterios con los que un usuario busca compañero/a de piso (relación 1:1 con
 * {@link Usuario}): rango de presupuesto y zona deseada, además de filtros
 * adicionales sobre convivencia.
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

    @Column(nullable = false)
    private String aceptaMascotas = "Sin preferencia";

    @Column(nullable = false)
    private String fumador = "Sin preferencia";

    @Column(nullable = false)
    private String nivelRuido = "Sin preferencia";

    @Column(nullable = false)
    private String visitas = "Sin preferencia";

    @Column(nullable = false)
    private String limpieza = "Sin preferencia";

    protected PreferenciasBusqueda() {
        // Requerido por JPA.
    }

    public PreferenciasBusqueda(Long usuarioId, int presupuestoMinimo,
                                int presupuestoMaximo, String zonaDeseada) {
        this.usuarioId = usuarioId;
        this.presupuestoMinimo = presupuestoMinimo;
        this.presupuestoMaximo = presupuestoMaximo;
        this.zonaDeseada = zonaDeseada;
        this.aceptaMascotas = "Sin preferencia";
        this.fumador = "Sin preferencia";
        this.nivelRuido = "Sin preferencia";
        this.visitas = "Sin preferencia";
        this.limpieza = "Sin preferencia";
    }

    public PreferenciasBusqueda(Long usuarioId, int presupuestoMinimo,
                                int presupuestoMaximo, String zonaDeseada,
                                String aceptaMascotas, String fumador, String nivelRuido,
                                String visitas, String limpieza) {
        this.usuarioId = usuarioId;
        this.presupuestoMinimo = presupuestoMinimo;
        this.presupuestoMaximo = presupuestoMaximo;
        this.zonaDeseada = zonaDeseada;
        this.aceptaMascotas = aceptaMascotas == null ? "Sin preferencia" : aceptaMascotas;
        this.fumador = fumador == null ? "Sin preferencia" : fumador;
        this.nivelRuido = nivelRuido == null ? "Sin preferencia" : nivelRuido;
        this.visitas = visitas == null ? "Sin preferencia" : visitas;
        this.limpieza = limpieza == null ? "Sin preferencia" : limpieza;
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

    public String getAceptaMascotas() {
        return aceptaMascotas;
    }

    public void setAceptaMascotas(String aceptaMascotas) {
        this.aceptaMascotas = aceptaMascotas == null ? "Sin preferencia" : aceptaMascotas;
    }

    public String getFumador() {
        return fumador;
    }

    public void setFumador(String fumador) {
        this.fumador = fumador == null ? "Sin preferencia" : fumador;
    }

    public String getNivelRuido() {
        return nivelRuido;
    }

    public void setNivelRuido(String nivelRuido) {
        this.nivelRuido = nivelRuido == null ? "Sin preferencia" : nivelRuido;
    }

    public String getVisitas() {
        return visitas;
    }

    public void setVisitas(String visitas) {
        this.visitas = visitas == null ? "Sin preferencia" : visitas;
    }

    public String getLimpieza() {
        return limpieza;
    }

    public void setLimpieza(String limpieza) {
        this.limpieza = limpieza == null ? "Sin preferencia" : limpieza;
    }
}
