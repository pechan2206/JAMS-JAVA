package com.example.web.jams.controladores;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.jams.dto.RecaudoPorPlanDTO;
import com.example.web.jams.dto.UsuariosPorPlanDTO;
import com.example.web.jams.modelos.SuscripcionModelo;
import com.example.web.jams.servicios.SuscripcionServicio;
import com.example.web.jams.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {

    private static final String VIEW_PATH = "Administrador/";
    private final SuscripcionServicio suscripcionServicio;
    private final UsuarioServicio usuarioServicio;

    @GetMapping("")
    public String index(Model model) {
        List<String> planes = suscripcionServicio.descripcionPlanes();
        model.addAttribute("descripciones", planes);

        List<UsuariosPorPlanDTO> totalUsuarios = suscripcionServicio.obtenerUsuariosPorPlan();
        model.addAttribute("usuarios", totalUsuarios);

        List<RecaudoPorPlanDTO> recaudoPlan = suscripcionServicio.obtenerRecaudoPorPlan();
        model.addAttribute("recaudo", recaudoPlan);
        return VIEW_PATH + "index";
    }

    @GetMapping("/exportar-excel")
    public void exportarUsuarios(HttpServletResponse response) throws IOException {
        usuarioServicio.exportarExcel(response);
    }

}
