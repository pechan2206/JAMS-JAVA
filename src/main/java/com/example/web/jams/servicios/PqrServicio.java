package com.example.web.jams.servicios;

import com.example.web.jams.modelos.PqrModelo;
import com.example.web.jams.repositorios.PqrRepositorio;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PqrServicio {

    private final PqrRepositorio pqrRepositorio;

    public PqrServicio(PqrRepositorio pqrRepositorio){
        this.pqrRepositorio = pqrRepositorio;
    }

    public List<PqrModelo> listarPqr(){
        return pqrRepositorio.findAll();
    }

}
