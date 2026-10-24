package com.pisomatch.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Vivienda publicada por un usuario (el propietario) para compartir.
 */
@Entity
@Table(name = "viviendas")
public class Vivienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "propietario_id", nullable = false)
    private Long propietarioId;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String zona;

    /** Precio mensual total de la vivienda, en euros. */
    @Column(nullable = false)
    private int precio;

    @Column(nullable = false)
    private int habitaciones;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vivienda_fotos", joinColumns = @JoinColumn(name = "vivienda_id"))
    @Column(name = "foto")
    private List<String> fotos = new ArrayList<>();

    protected Vivienda() {
        // Requerido por JPA.
    }

    public Vivienda(Long propietarioId, String direccion, String zona, int precio,
                    int habitaciones, List<String> fotos) {
        this.propietarioId = propietarioId;
        this.direccion = direccion;
        this.zona = zona;
        this.precio = precio;
        this.habitaciones = habitaciones;
        this.fotos = fotos != null ? new ArrayList<>(fotos) : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Long getPropietarioId() {
        return propietarioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos != null ? new ArrayList<>(fotos) : new ArrayList<>();
    }
}
