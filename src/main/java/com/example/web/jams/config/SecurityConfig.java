package com.example.web.jams.config;

import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.servicios.UsuarioServicio;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UsuarioServicio usuarioServicio;

    public SecurityConfig(@Lazy UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Cambiar a NoOpPasswordEncoder si tus contraseñas no están encriptadas
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioServicio);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/", "/login", "/registro", "/registro/guardar",
                                "/css/**", "/js/**", "/images/**", "/webjars/**")
                        .permitAll()

                        // Rutas solo admin
                        .requestMatchers("/administrador/**", "/usuarios/**", "/suscripciones/**", "/soporte/**")
                        .hasRole("Administrador")

                        // Rutas solo cliente
                        .requestMatchers("/Cliente/**").hasRole("Cliente")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("nombreUsuario")
                        .passwordParameter("pswUsuario")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            var roles = authentication.getAuthorities().toString();
                            String nombreUsuario = authentication.getName();
                            UsuarioModelo usuario = usuarioServicio.buscarPorNombreUsuario(nombreUsuario);

                            if (usuario != null) {
                                request.getSession().setAttribute("idUsuario", usuario.getIdUsuario());
                                request.getSession().setAttribute("nombreUsuario", usuario.getNombreUsuario());
                            }

                            // Redirección según rol
                            if (roles.contains("ROLE_Administrador")) {
                                response.sendRedirect("/administrador");
                            } else if (roles.contains("ROLE_Cliente")) {
                                response.sendRedirect("/Cliente");
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                        .failureUrl("/login?error=true"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Si no hay sesión, redirige al login con parámetro
                            response.sendRedirect("/login?accesoDenegado=true");
                        }))
                .csrf(csrf -> csrf.disable()); // Solo para pruebas, habilitar en producción

        return http.build();
    }
}
