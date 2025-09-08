package com.example.web.jams.repositorios;

import com.example.web.jams.modelos.SuscripcionModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuscripcionRepositorio extends JpaRepository<SuscripcionModelo,Long> {
}
