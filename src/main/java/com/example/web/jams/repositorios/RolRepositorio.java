package com.example.web.jams.repositorios;

import com.example.web.jams.modelos.RolModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<RolModelo, Long> {

}
