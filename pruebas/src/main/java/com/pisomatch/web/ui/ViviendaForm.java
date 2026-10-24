package com.pisomatch.web.ui;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

public class ViviendaForm {

    @NotBlank
    private String direccion;

    @NotBlank
    private String zona;

    @Positive
    private int precio;

    @PositiveOrZero
    private int habitaciones;

    private String fotos;

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

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public List<String> getFotosLista() {
        if (fotos == null || fotos.isBlank()) {
            return new ArrayList<>();
        }
        List<String> lista = new ArrayList<>();
        for (String f : fotos.split("\\n|,")) {
            String t = f.trim();
            if (!t.isEmpty()) {
                lista.add(t);
            }
        }
        return lista;
    }
}
