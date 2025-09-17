package com.example.web.jams.servicios;

import com.example.web.jams.modelos.SolicitudMensajeModelo;
import com.example.web.jams.repositorios.SolicitudMensajeRepositorio;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SolicitudMensajeServicio {
    private final SolicitudMensajeRepositorio solicitudMensajeRepositorio;

    public SolicitudMensajeServicio(SolicitudMensajeRepositorio solicitudMensajeRepositorio) {
        this.solicitudMensajeRepositorio = solicitudMensajeRepositorio;
    }

    public SolicitudMensajeModelo guardarMensaje(SolicitudMensajeModelo mensaje){
        return solicitudMensajeRepositorio.save(mensaje);
    }

    public List<SolicitudMensajeModelo> listasMensajes(){
        return solicitudMensajeRepositorio.findAll();
    }

}
