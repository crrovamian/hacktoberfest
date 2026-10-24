package com.pisomatch.repository;

import com.pisomatch.domain.EstadoLike;
import com.pisomatch.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUsuarioIdAndUsuarioObjetivoId(Long usuarioId, Long usuarioObjetivoId);

    List<Like> findByUsuarioId(Long usuarioId);

    List<Like> findByUsuarioIdAndEstado(Long usuarioId, EstadoLike estado);
}
