package com.example.web.jams.controladores;

import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.servicios.SuscripcionServicio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/suscripciones")
public class SuscripcionControlador {

    private final SuscripcionServicio suscripcionServicio;
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
    public String eliminar(@PathVariable("id") Long id) {
        suscripcionServicio.eliminar(id);
        return "redirect:/suscripciones";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("suscripcion") SuscripcionModelo datos,
                             Model model,
                             BindingResult result) {

        // Validación de duplicado en actualización
        SuscripcionModelo existente = suscripcionServicio.buscarPorDescripcion(datos.getDescripcion());
        if (existente != null && !existente.getIdSuscripcion().equals(id)) {
            result.rejectValue("descripcion", "error.descripcion", "La descripción ya está registrada");
        }

        if (result.hasErrors()) {
            model.addAttribute("suscripcion", datos);
            return VIEW_PATH + "editar";
        }

        suscripcionServicio.actualizar(id, datos);
        return "redirect:/suscripciones";
    }

    @GetMapping("/formulario")
    public String formularioSuscripcion(Model model) {
        model.addAttribute("suscripcion", new SuscripcionModelo());
        return VIEW_PATH + "formulario";
    }

    @PostMapping("/guardarSuscripcion")
    public String guardar(@Valid @ModelAttribute("suscripcion") SuscripcionModelo suscripcionNueva,
                          BindingResult result,
                          Model model) {

        // Validación de duplicado en creación
        if (suscripcionServicio.existeDescripcion(suscripcionNueva.getDescripcion())) {
            result.rejectValue("descripcion", "error.descripcion", "La descripción ya está registrada");
        }

        if (result.hasErrors()) {
            model.addAttribute("suscripcion", suscripcionNueva);
            return VIEW_PATH + "formulario";
        }

        suscripcionServicio.guardar(suscripcionNueva);
        return "redirect:/suscripciones";
    }
}
