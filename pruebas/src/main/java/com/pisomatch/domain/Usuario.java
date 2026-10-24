package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Persona que usa la aplicación. Contiene únicamente los datos de identidad;
 * sus preferencias de convivencia y de búsqueda viven en entidades aparte
 * ({@link PerfilConvivencia} y {@link PreferenciasBusqueda}).
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int edad;

    private String foto;

    private String profesion;

    @Column(length = 500)
    private String descripcion;

    protected Usuario() {
        // Requerido por JPA.
    }

    public Usuario(String nombre, int edad, String foto, String profesion, String descripcion) {
        this.nombre = nombre;
        this.edad = edad;
        this.foto = foto;
        this.profesion = profesion;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
