package com.example.web.jams.config;

import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.repositorios.UsuarioRepositorio;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public CustomUserDetailsService(UsuarioRepositorio usuarioRepositorio) {    
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aquí usamos Optional para evitar el error de tipo incompatible
        UsuarioModelo usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construir autoridad (ROLE_Cliente, ROLE_Administrador, etc.)
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                "ROLE_" + (usuario.getRol() != null ? usuario.getRol().getDescripcion() : "Cliente"));

        return new User(
                usuario.getNombreUsuario(),
                usuario.getPswUsuario(),
                Collections.singletonList(authority)
        );
    }
}
