package com.example.web.jams.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.repositorios.SuscripcionUsuarioRepositorio;
import com.example.web.jams.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio,
            SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.suscripcionUsuarioRepositorio = suscripcionUsuarioRepositorio;
    }

    public List<UsuarioModelo> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public UsuarioModelo buscarPorId(Long id) {
        return usuarioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el id" + id));
    }

    @Transactional
    public void eliminar(Long id) {
        suscripcionUsuarioRepositorio.deleteByUsuarioId(id);
        usuarioRepositorio.deleteById(id);
    }

    public UsuarioModelo actualizar(Long id, UsuarioModelo datos) {
        UsuarioModelo existente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ✅ Actualizar solo los campos que vienen del form
        existente.setNombreUsuario(datos.getNombreUsuario());
        existente.setCorreoUsuario(datos.getCorreoUsuario());
        existente.setTelefonoUsuario(datos.getTelefonoUsuario());
        existente.setRestaurante(datos.getRestaurante());

        return usuarioRepositorio.save(existente);
    }

}
