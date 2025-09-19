package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.jams.dto.SuscripcionesPorUsuarioDTO;
import com.example.web.jams.modelos.PqrModelo;
import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.servicios.PqrServicio;
import com.example.web.jams.servicios.SuscripcionServicio;
import com.example.web.jams.servicios.SuscripcionUsuarioServicio;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@Controller
@RequestMapping("/Cliente")
public class ClienteControlador {

    private final PqrServicio pqrServicio;
    private final SuscripcionServicio suscripcionServicio;
    private final SuscripcionUsuarioServicio suscripcionUsuarioServicio;


    @GetMapping("")
    public String cliente(Model model, HttpSession session) {
        // Traer usuario logueado (ejemplo: desde la sesión)
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        // 2. Buscar sus suscripciones
        List<SuscripcionesPorUsuarioDTO> suscripciones = suscripcionServicio.obtenerPorUsuario(idUsuario);
        model.addAttribute("suscripciones", suscripciones);

        // 3. Si no tiene suscripciones, mostramos todos los planes
        if (suscripciones.isEmpty()) {
            List<SuscripcionModelo> planes = suscripcionServicio.listarSuscripciones();
            model.addAttribute("planes", planes);
        }

        return "cliente";
    }

    @PostMapping("/suscribirse")
    public String suscribirse(@RequestParam("idSuscripcion") Long idSuscripcion, HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("id_usuario");

        suscripcionUsuarioServicio.insertarSuscripcionUsuario(idUsuario, idSuscripcion);

        return "redirect:/cliente";
    }

    @PostMapping("/enviarPqr")
    public String guardarPqr(HttpSession session, @ModelAttribute PqrModelo pqr) {
        String nombreUsuario = (String) session.getAttribute("usuario");

        // Asignar directamente el nombre del usuario a la PQR
        pqr.setIdUsuario(nombreUsuario);

        // Guardar la PQR
        pqrServicio.enviarPqr(pqr);
        return "redirect:/cliente";
    }

}
