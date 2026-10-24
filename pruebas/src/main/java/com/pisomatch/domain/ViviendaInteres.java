package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

@Entity
@Table(name = "vivienda_intereses", uniqueConstraints =
        @UniqueConstraint(columnNames = {"usuario_id", "vivienda_id"}))
public class ViviendaInteres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "vivienda_id", nullable = false)
    private Long viviendaId;

    @Column(nullable = false)
    private Instant fecha;

    protected ViviendaInteres() {
    }

    public ViviendaInteres(Long usuarioId, Long viviendaId) {
        this.usuarioId = usuarioId;
        this.viviendaId = viviendaId;
        this.fecha = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getViviendaId() {
        return viviendaId;
    }

    public Instant getFecha() {
        return fecha;
    }
}
