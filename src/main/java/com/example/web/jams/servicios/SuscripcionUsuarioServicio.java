package com.example.web.jams.servicios;

import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.modelos.SuscripcionUsuarioModelo;
import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.repositorios.SuscripcionRepositorio;
import com.example.web.jams.repositorios.SuscripcionUsuarioRepositorio;
import com.example.web.jams.repositorios.UsuarioRepositorio;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SuscripcionUsuarioServicio {

    private final SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final SuscripcionRepositorio suscripcionRepositorio;


    public void insertarSuscripcionUsuario(Long idUsuario, Long idSuscripcion) {
        UsuarioModelo usuario = usuarioRepositorio.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        SuscripcionModelo suscripcion = suscripcionRepositorio.findById(idSuscripcion)
            .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));

        SuscripcionUsuarioModelo suscripcionUsuario = new SuscripcionUsuarioModelo();
        suscripcionUsuario.setUsuario(usuario);
        suscripcionUsuario.setSuscripcion(suscripcion);
        suscripcionUsuario.setFechaCompra(LocalDateTime.now());

        suscripcionUsuarioRepositorio.save(suscripcionUsuario);
    }


    
}
