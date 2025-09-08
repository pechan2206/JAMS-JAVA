package com.example.web.jams.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.web.jams.modelos.UsuarioModelo;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo,Long>{

    UsuarioModelo findByNombreUsuario(String nombreUsuario);

}
