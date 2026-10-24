package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Mensaje de chat enviado de un usuario (emisor) a otro (receptor).
 * Solo se permite entre usuarios que ya han hecho match.
 */
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emisor_id", nullable = false)
    private Long emisorId;

    @Column(name = "receptor_id", nullable = false)
    private Long receptorId;

    @Column(nullable = false, length = 2000)
    private String contenido;

    @Column(nullable = false)
    private Instant fecha;

    protected Mensaje() {
        // Requerido por JPA.
    }

    public Mensaje(Long emisorId, Long receptorId, String contenido) {
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.contenido = contenido;
        this.fecha = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getEmisorId() {
        return emisorId;
    }

    public Long getReceptorId() {
        return receptorId;
    }

    public String getContenido() {
        return contenido;
    }

    public Instant getFecha() {
        return fecha;
    }
}
