package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.servicios.SuscripcionServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/suscripciones")
public class SuscripcionControlador {

    public final SuscripcionServicio suscripcionServicio;
    private static final String VIEW_PATH = "Suscripciones/";

    public SuscripcionControlador(SuscripcionServicio suscripcionServicio) {
        this.suscripcionServicio = suscripcionServicio;
    }

    @GetMapping("")
    public String suscripciones(Model model) {
        List<SuscripcionModelo> suscripciones = suscripcionServicio.listarSuscripciones();
        model.addAttribute("suscripciones", suscripciones);
        return VIEW_PATH + "suscripciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        SuscripcionModelo suscripcion = suscripcionServicio.buscarPorId(id);
        model.addAttribute("suscripcion", suscripcion);
        return VIEW_PATH + "editar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id){
        suscripcionServicio.eliminar(id);
        return "redirect:/suscripciones";
    }

    @PostMapping("/actualizar/{id}")
    public String guardar(@ModelAttribute SuscripcionModelo datos){
        suscripcionServicio.guardar(datos);
        return "redirect:/sucripciones";
    }

}
