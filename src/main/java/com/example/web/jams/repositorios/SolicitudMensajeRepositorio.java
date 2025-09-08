package com.example.web.jams.repositorios;


import com.example.web.jams.modelos.SolicitudMensajeModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudMensajeRepositorio extends JpaRepository<SolicitudMensajeModelo,Long> {

}
