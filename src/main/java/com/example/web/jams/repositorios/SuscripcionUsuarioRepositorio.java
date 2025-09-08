package com.example.web.jams.repositorios;

import com.example.web.jams.modelos.SuscripcionUsuarioModelo;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SuscripcionUsuarioRepositorio extends JpaRepository<SuscripcionUsuarioModelo, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM SuscripcionUsuarioModelo su WHERE su.usuario.id = :idUsuario")
    void deleteByUsuarioId(Long idUsuario);
}
