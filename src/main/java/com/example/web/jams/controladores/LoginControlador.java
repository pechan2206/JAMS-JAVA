package com.example.web.jams.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.web.jams.modelos.RolModelo;
import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.servicios.UsuarioServicio;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class LoginControlador {

    private final UsuarioServicio usuarioServicio;

    // Vista de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registrarse(Model model) {
        model.addAttribute("usuario", new UsuarioModelo());
        return "registrar";
    }

    // Guardar nuevo cliente
    @PostMapping("/registro/guardar")
    public String guardarRegistro(@Valid @ModelAttribute("usuario") UsuarioModelo usuario,
                                  BindingResult result,
                                  Model model, RedirectAttributes redirect) {

        // Validaciones de unicidad
        if (usuarioServicio.existeNombreUsuario(usuario.getNombreUsuario())) {
            result.rejectValue("nombreUsuario", "error.nombreUsuario", "Este nombre de usuario ya está registrado");
        }
        if (usuarioServicio.existeTelefono(usuario.getTelefonoUsuario())) {
            result.rejectValue("telefonoUsuario", "error.telefonoUsuario", "Este teléfono ya está registrado");
        }
        if (usuarioServicio.existeCorreo(usuario.getCorreoUsuario())) {
            result.rejectValue("correoUsuario", "error.correoUsuario", "Este correo ya está registrado");
        }
        if (usuarioServicio.existeRestaurante(usuario.getRestaurante())) {
            result.rejectValue("restaurante", "error.restaurante", "Este restaurante ya está registrado");
        }

        if (result.hasErrors()) {
            System.out.println("Errores de validación detectados:");
            result.getAllErrors().forEach(err -> System.out.println(err.toString()));

            model.addAttribute("usuario", usuario);
            return "registrar";
        }

        RolModelo rolCliente = new RolModelo();
        rolCliente.setIdRol(2L); // Rol por defecto: Cliente
        usuario.setRol(rolCliente);

        usuarioServicio.guardarUsuario(usuario);

        redirect.addFlashAttribute("exito", "Registro con éxito");

        return "redirect:/login";
    }
}
