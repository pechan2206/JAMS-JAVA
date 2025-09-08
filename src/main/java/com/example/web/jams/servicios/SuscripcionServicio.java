package com.example.web.jams.servicios;

import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.repositorios.SuscripcionRepositorio;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SuscripcionServicio {
    private final SuscripcionRepositorio suscripcionRepositorio;

    public SuscripcionServicio(SuscripcionRepositorio suscripcionRepositorio) {
        this.suscripcionRepositorio = suscripcionRepositorio;
    }

    public List<SuscripcionModelo> listarSuscripciones() {
        return suscripcionRepositorio.findAll();
    }

    public SuscripcionModelo buscarPorId(Long id) {
        return suscripcionRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el id" + id));
    }

    @Transactional
    public void eliminar(Long id) {
        suscripcionRepositorio.deleteById(id);
    }

    public SuscripcionModelo actualizar(Long id, SuscripcionModelo datos) {
        SuscripcionModelo existente = suscripcionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());

        return suscripcionRepositorio.save(existente);
    }

    public SuscripcionModelo guardar(SuscripcionModelo datos){
        return suscripcionRepositorio.save(datos);
        
    }
}
