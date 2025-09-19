package com.example.web.jams.servicios;

import com.example.web.jams.dto.RecaudoPorPlanDTO;
import com.example.web.jams.dto.SuscripcionesPorUsuarioDTO;
import com.example.web.jams.dto.UsuariosPorPlanDTO;
import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.repositorios.SuscripcionRepositorio;
import com.example.web.jams.repositorios.SuscripcionUsuarioRepositorio;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SuscripcionServicio {
    private final SuscripcionRepositorio suscripcionRepositorio;
    private final SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio;

    public SuscripcionServicio(SuscripcionRepositorio suscripcionRepositorio, SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio) {
        this.suscripcionRepositorio = suscripcionRepositorio;
        this.suscripcionUsuarioRepositorio = suscripcionUsuarioRepositorio;
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
                .orElseThrow(() -> new RuntimeException("Suscripcion no encontrada"));

        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());

        return suscripcionRepositorio.save(existente);
    }

    public SuscripcionModelo guardar(SuscripcionModelo datos) {
        return suscripcionRepositorio.save(datos);
    }

    public List<UsuariosPorPlanDTO> obtenerUsuariosPorPlan() {
        return suscripcionRepositorio.contarUsuariosPorPlan();
    }

    public List<RecaudoPorPlanDTO> obtenerRecaudoPorPlan() {
        return suscripcionRepositorio.recaudoPorPlan();
    }

    public List<String> descripcionPlanes() {
        return suscripcionRepositorio.findAllDescripciones();
    }

    public List<SuscripcionesPorUsuarioDTO> obtenerPorUsuario(Long idUsuario){
        return suscripcionUsuarioRepositorio.obtenerSuscripcionesPorUsuario(idUsuario);
    }


}
