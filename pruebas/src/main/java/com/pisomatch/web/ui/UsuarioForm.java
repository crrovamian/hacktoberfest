package com.pisomatch.web.ui;

import com.pisomatch.web.dto.UsuarioRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Bean de formulario (mutable) para dar de alta un usuario desde las vistas Thymeleaf.
 */
public class UsuarioForm {

    @NotBlank
    private String nombre;

    @Min(18)
    @Max(120)
    private int edad = 25;

    private String foto;

    private String profesion;

    @Size(max = 500)
    private String descripcion;

    public UsuarioRequest toRequest() {
        return new UsuarioRequest(nombre, edad, foto, profesion, descripcion);
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
