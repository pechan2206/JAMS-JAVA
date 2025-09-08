package com.example.web.jams.servicios;

import com.example.web.jams.repositorios.SuscripcionUsuarioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class SuscripcionUsuarioServicio {

    private final SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio;

    public SuscripcionUsuarioServicio(SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio) {
        this.suscripcionUsuarioRepositorio = suscripcionUsuarioRepositorio;
    }



}
