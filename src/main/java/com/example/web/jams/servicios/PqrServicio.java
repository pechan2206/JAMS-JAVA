package com.example.web.jams.servicios;

import com.example.web.jams.modelos.PqrModelo;

import com.example.web.jams.repositorios.PqrRepositorio;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PqrServicio {

    private final PqrRepositorio pqrRepositorio;

    public List<PqrModelo> listarPqr() {
        return pqrRepositorio.findAll();
    }

    public PqrModelo actualizarEstado(Long id) {
        PqrModelo validar = pqrRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pqr no encotrada"));
        validar.setEstado("validado");
        return pqrRepositorio.save(validar);
    }

    public void enviarPqr(PqrModelo pqr) {
        pqrRepositorio.save(pqr);
    }
}
