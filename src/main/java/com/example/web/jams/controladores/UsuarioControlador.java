package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.web.jams.modelos.RolModelo;
import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.servicios.UsuarioServicio;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    public final UsuarioServicio usuarioServicio;
    private static final String VIEW_PATH = "Usuarios/";

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("")
    public String usuarios(Model model) {
        List<UsuarioModelo> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return VIEW_PATH + "usuarios";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(
            @Valid @ModelAttribute("usuario") UsuarioModelo usuario,
            BindingResult result,
            Model model, RedirectAttributes redirect) {

        // 1️⃣ Validaciones de duplicados (crear)
        validarDuplicadosCrear(usuario, result);

        // 2️⃣ Validación de restaurante solo para usuarios normales
        if (usuario.getRol() == null || usuario.getRol().getIdRol() != 1L) {
            if (usuario.getRestaurante() != null && !usuario.getRestaurante().isBlank()) {
                if (usuarioServicio.existeRestaurante(usuario.getRestaurante())) {
                    result.rejectValue("restaurante", "error.restaurante", "Este restaurante ya está registrado");
                }
            }
        }

        // 3️⃣ Revisar si hay errores
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "Usuarios/agregar";
        }

        // 4️⃣ Asignar rol si es usuario normal
        if (usuario.getRol() == null || usuario.getRol().getIdRol() != 1L) {
            RolModelo rol = new RolModelo();
            rol.setIdRol(2L); // rol usuario normal
            usuario.setRol(rol);
        }

        // 5️⃣ Guardar usuario
        usuarioServicio.guardarUsuario(usuario);
        redirect.addFlashAttribute("exito", "Registro realizado");

        return "redirect:/usuarios";
    }

    // Método privado para validar duplicados (crear)
    private void validarDuplicadosCrear(UsuarioModelo usuario, BindingResult result) {
        if (usuarioServicio.existeNombreUsuario(usuario.getNombreUsuario())) {
            result.rejectValue("nombreUsuario", "error.nombreUsuario", "Este nombre de usuario ya está registrado");
        }

        if (usuarioServicio.existeTelefono(usuario.getTelefonoUsuario())) {
            result.rejectValue("telefonoUsuario", "error.telefonoUsuario", "Este teléfono ya está registrado");
        }

        if (usuarioServicio.existeCorreo(usuario.getCorreoUsuario())) {
            result.rejectValue("correoUsuario", "error.correoUsuario", "Este correo ya está registrado");
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        UsuarioModelo usuario = usuarioServicio.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return VIEW_PATH + "editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable("id") Long id,
            @Valid @ModelAttribute("usuario") UsuarioModelo datos,
            BindingResult result, // Siempre inmediatamente después de @Valid
            Model model) {

        validarDuplicadosActualizar(datos, result, id);

        if (result.hasErrors()) {
            datos.setIdUsuario(id);
            model.addAttribute("usuario", datos);
            return VIEW_PATH + "editar";
        }

        usuarioServicio.actualizar(id, datos);
        return "redirect:/usuarios";
    }

    /* Metodo para validar y actualizar el registro del usuario */
    private void validarDuplicadosActualizar(UsuarioModelo usuario, BindingResult result, Long idActual) {
        if (usuarioServicio.existeNombreUsuario(usuario.getNombreUsuario())) {
            UsuarioModelo existente = usuarioServicio.buscarPorNombreUsuario(usuario.getNombreUsuario());
            if (existente != null && !existente.getIdUsuario().equals(idActual)) {
                result.rejectValue("nombreUsuario", "error.nombreUsuario", "Este nombre de usuario ya está registrado");
            }
        }

        if (usuarioServicio.existeTelefono(usuario.getTelefonoUsuario())) {
            UsuarioModelo existente = usuarioServicio.buscarPorTelefonoUsuario(usuario.getTelefonoUsuario());
            if (existente != null && !existente.getIdUsuario().equals(idActual)) {
                result.rejectValue("telefonoUsuario", "error.telefonoUsuario", "Este teléfono ya está registrado");
            }
        }

        if (usuarioServicio.existeCorreo(usuario.getCorreoUsuario())) {
            UsuarioModelo existente = usuarioServicio.buscarPorCorreoUsuario(usuario.getCorreoUsuario());
            if (existente != null && !existente.getIdUsuario().equals(idActual)) {
                result.rejectValue("correoUsuario", "error.correoUsuario", "Este correo ya está registrado");
            }
        }

        // Solo aplica la validación de restaurante si no es admin
        if (usuario.getRol() == null || usuario.getRol().getIdRol() != 1L) {
            if (usuario.getRestaurante() != null && !usuario.getRestaurante().isBlank()) {
                if (usuarioServicio.existeRestaurante(usuario.getRestaurante())) {
                    UsuarioModelo existente = usuarioServicio.buscarPorRestaurante(usuario.getRestaurante());
                    if (existente != null && !existente.getIdUsuario().equals(idActual)) {
                        result.rejectValue("restaurante", "error.restaurante", "El restaurante ya está registrado");
                    }
                }
            }
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        usuarioServicio.eliminar(id);
        return "redirect:/usuarios";
    }

    @GetMapping("/agregar")
    public String agregarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioModelo());
        return VIEW_PATH + "agregar";
    }

}
