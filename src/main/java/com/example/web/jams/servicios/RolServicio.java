package com.example.web.jams.servicios;

import com.example.web.jams.repositorios.RolRepositorio;
import org.springframework.stereotype.Service;

@Service
public class RolServicio {
    private final RolRepositorio rolRepositorio;

    public RolServicio(RolRepositorio rolRepositorio) {
        this.rolRepositorio = rolRepositorio;
    }
}
