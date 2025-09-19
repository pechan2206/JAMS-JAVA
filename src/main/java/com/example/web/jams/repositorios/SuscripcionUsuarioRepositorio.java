package com.example.web.jams.repositorios;

import com.example.web.jams.dto.SuscripcionesPorUsuarioDTO;
import com.example.web.jams.modelos.SuscripcionUsuarioModelo;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuscripcionUsuarioRepositorio extends JpaRepository<SuscripcionUsuarioModelo, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SuscripcionUsuarioModelo su WHERE su.usuario.id = :idUsuario")
    void deleteByUsuarioId(Long idUsuario);

    @Query("SELECT s.descripcion AS descripcion, " +
            "       s.precio AS precio, " +
            "       s.fechaModificacion AS fechaModificacion, " +
            "       su.fechaCompra AS fechaCompra " +
            "FROM SuscripcionUsuarioModelo su " +
            "JOIN su.suscripcion s " +
            "WHERE su.usuario.id = :idUsuario")
    List<SuscripcionesPorUsuarioDTO> obtenerSuscripcionesPorUsuario(@Param("idUsuario") Long idUsuario);
}
