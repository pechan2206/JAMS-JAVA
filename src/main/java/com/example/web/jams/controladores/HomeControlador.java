package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.web.jams.modelos.SolicitudMensajeModelo;
import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.servicios.SolicitudMensajeServicio;
import com.example.web.jams.servicios.SuscripcionServicio;
import com.example.web.jams.servicios.UsuarioServicio;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeControlador {

    public final UsuarioServicio usuarioServicio;
    public final SuscripcionServicio suscripcionServicio;
    public final SolicitudMensajeServicio solicitudMensajeServicio;

    public HomeControlador(UsuarioServicio usuarioServicio, SuscripcionServicio suscripcionServicio,
            SolicitudMensajeServicio solicitudMensajeServicio) {
        this.suscripcionServicio = suscripcionServicio;
        this.usuarioServicio = usuarioServicio;
        this.solicitudMensajeServicio = solicitudMensajeServicio;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<SuscripcionModelo> suscripciones = suscripcionServicio.listarSuscripciones();
        model.addAttribute("suscripciones", suscripciones);
        return "home";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute UsuarioModelo usuario) {
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/";
    }

    @GetMapping("/formulario")
    public String getMethodName(Model model) {
        model.addAttribute("usuario", new UsuarioModelo());
        return "formulario";
    }

    @PostMapping("/guardarSolicitud")
    public String guardarSolicitud(@ModelAttribute SolicitudMensajeModelo solicitud) {

        // Verifica si al menos hay algo diligenciado antes de guardar
        if (solicitud.getNombre() != null ||
                solicitud.getCorreo() != null ||
                solicitud.getMensaje() != null) {

            solicitudMensajeServicio.guardarMensaje(solicitud);
        }
        return "redirect:/"; // vuelve al home
    }



}
