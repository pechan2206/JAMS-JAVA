package com.example.web.jams.repositorios;

import com.example.web.jams.modelos.PqrModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PqrRepositorio extends JpaRepository<PqrModelo,Long> {

}
