package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web.jams.modelos.PqrModelo;
import com.example.web.jams.modelos.SolicitudMensajeModelo;
import com.example.web.jams.servicios.PqrServicio;
import com.example.web.jams.servicios.SolicitudMensajeServicio;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/soporte")
public class SoporteController {

    public final PqrServicio pqrServicio;
    public final SolicitudMensajeServicio solicitudMensajeServicio;

    private static final String VIEW_PATH = "Soporte/";

    @GetMapping("/mensajes")
    public String mensajes(Model model) {
        List<SolicitudMensajeModelo> mensajes = solicitudMensajeServicio.listasMensajes();
        model.addAttribute("mensajes", mensajes);
        return VIEW_PATH + "mensajes";
    }

    @GetMapping("/validarMensaje/{id}")
    public String validarMensaje(@PathVariable("id") Long id) {
        solicitudMensajeServicio.actualizarEstado(id);
        return "redirect:/soporte/mensajes";
    }

    @GetMapping("/pqrs")
    public String pqrs(Model model) {
        List<PqrModelo> pqrs = pqrServicio.listarPqr();
        model.addAttribute("pqr", pqrs);
        return VIEW_PATH + "pqr";
    }

    @GetMapping("/validarPqr/{id}")
    public String validarPqr(@PathVariable("id") Long id) {
        pqrServicio.actualizarEstado(id);
        return "redirect:/soporte/pqrs";
    }

}