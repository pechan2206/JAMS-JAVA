package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.jams.dto.SuscripcionesPorUsuarioDTO;
import com.example.web.jams.modelos.PqrModelo;
import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.servicios.PqrServicio;
import com.example.web.jams.servicios.SuscripcionServicio;
import com.example.web.jams.servicios.SuscripcionUsuarioServicio;

// Para Spring Security
import org.springframework.security.core.Authentication;

import com.example.web.jams.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@Controller
@RequestMapping("/Cliente")
public class ClienteControlador {

    private final PqrServicio pqrServicio;
    private final SuscripcionServicio suscripcionServicio;
    private final SuscripcionUsuarioServicio suscripcionUsuarioServicio;
    private final UsuarioServicio usuarioServicio;

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
        model.addAttribute("pqr", new PqrModelo());

        return "cliente";
    }

    @PostMapping("/suscribirse/{idSuscripcion}")
    public String suscribirse(@PathVariable("idSuscripcion") Long idSuscripcion, HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");

        if (idUsuario == null || nombreUsuario == null) {
            return "redirect:/login";
        }
        suscripcionUsuarioServicio.insertarSuscripcionUsuario(idUsuario, idSuscripcion);

        return "redirect:/Cliente?success=suscripcionExitosa";
    }


    @PostMapping("/enviarPqr")
    public String guardarPqr(@Valid @ModelAttribute("pqr") PqrModelo pqr,
            BindingResult result,
            HttpSession session,
            Model model) {

        // Si hay errores de validación, vuelve al formulario
        if (result.hasErrors()) {
            model.addAttribute("pqr", pqr);
            return "cliente"; // o la vista donde esté el formulario
        }

        // Recuperar el usuario de la sesión
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        pqr.setIdUsuario(nombreUsuario);

        // Guardar PQR
        pqrServicio.enviarPqr(pqr);

        return "redirect:/Cliente";
    }

}
