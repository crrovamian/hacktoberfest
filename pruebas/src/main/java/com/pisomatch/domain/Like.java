package com.pisomatch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

/**
 * Decisión dirigida de un usuario sobre otro (like / pass) y su estado.
 * Un usuario solo puede valorar una vez a otro. Cuando el like es recíproco,
 * ambas filas (una por sentido) pasan a {@link EstadoLike#MATCH}.
 */
@Entity
@Table(name = "likes", uniqueConstraints =
        @UniqueConstraint(columnNames = {"usuario_id", "usuario_objetivo_id"}))
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "usuario_objetivo_id", nullable = false)
    private Long usuarioObjetivoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLike estado;

    @Column(nullable = false)
    private Instant fecha;

    protected Like() {
        // Requerido por JPA.
    }

    public Like(Long usuarioId, Long usuarioObjetivoId, EstadoLike estado) {
        this.usuarioId = usuarioId;
        this.usuarioObjetivoId = usuarioObjetivoId;
        this.estado = estado;
        this.fecha = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getUsuarioObjetivoId() {
        return usuarioObjetivoId;
    }

    public EstadoLike getEstado() {
        return estado;
    }

    public void setEstado(EstadoLike estado) {
        this.estado = estado;
    }

    public Instant getFecha() {
        return fecha;
    }
}
