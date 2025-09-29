package com.example.web.jams.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.web.jams.modelos.UsuarioModelo;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo,Long>{
    
    Optional<UsuarioModelo> findByNombreUsuario(String nombreUsuario);

    Optional<UsuarioModelo> findByTelefonoUsuario(String telefonoUsuario);

    Optional<UsuarioModelo> findByCorreoUsuario(String correoUsuario);

    Optional<UsuarioModelo> findByRestaurante(String restaurante);

    boolean existsByNombreUsuario(String nombreUsuario);

    boolean existsByTelefonoUsuario(String telefonoUsuario);

    boolean existsByCorreoUsuario(String correoUsuario);

    boolean existsByRestaurante(String restaurante);
}
