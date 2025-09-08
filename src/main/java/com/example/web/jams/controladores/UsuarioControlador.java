package com.example.web.jams.controladores;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.web.jams.modelos.RolModelo;
import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.servicios.UsuarioServicio;
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
    public String guardarUsuario(@ModelAttribute UsuarioModelo usuario) {
        RolModelo rol = new RolModelo();
        rol.setIdRol(1L);
        usuario.setRol(rol);
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/";
    }

    @GetMapping("/formulario")
    public String getMethodName(Model model) {
        model.addAttribute("usuario", new UsuarioModelo());
        return VIEW_PATH + "formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        UsuarioModelo usuario = usuarioServicio.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return VIEW_PATH + "editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable("id") Long id, @ModelAttribute UsuarioModelo datos, Model model) {
        usuarioServicio.actualizar(id, datos);
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        usuarioServicio.eliminar(id);
        return "redirect:/usuarios";
    }

}
