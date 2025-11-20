package com.example.web.jams.servicios;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.repositorios.SuscripcionUsuarioRepositorio;
import com.example.web.jams.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio,
            SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.suscripcionUsuarioRepositorio = suscripcionUsuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    /** --- UserDetailsService --- */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModelo usuario = usuarioRepositorio.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority(
                "ROLE_" + (usuario.getRol() != null ? usuario.getRol().getDescripcion() : "Cliente"));

        return new User(
                usuario.getNombreUsuario(),
                usuario.getPswUsuario(),
                Collections.singletonList(authority));
    }

    /** Listar todos los usuarios */
    public List<UsuarioModelo> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    /** Guardar usuario (con encriptación de contraseña) */
    @Transactional
    public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
        usuario.setPswUsuario(passwordEncoder.encode(usuario.getPswUsuario()));
        return usuarioRepositorio.save(usuario);
    }

    /** Buscar usuario por ID */
    public UsuarioModelo buscarPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id " + id));
    }

    /** Eliminar usuario y sus suscripciones relacionadas */
    @Transactional
    public void eliminar(Long id) {
        suscripcionUsuarioRepositorio.deleteByUsuarioId(id);
        usuarioRepositorio.deleteById(id);
    }

    /** Actualizar datos de usuario */
    public UsuarioModelo actualizar(Long id, UsuarioModelo datos) {
        UsuarioModelo existente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existente.setNombreUsuario(datos.getNombreUsuario());
        existente.setCorreoUsuario(datos.getCorreoUsuario());
        existente.setPswUsuario(datos.getPswUsuario());
        existente.setTelefonoUsuario(datos.getTelefonoUsuario());
        existente.setRestaurante(datos.getRestaurante());

        return usuarioRepositorio.save(existente);
    }

    /** --- Métodos de verificación --- */
    public boolean existeNombreUsuario(String usuario) {
        return usuarioRepositorio.existsByNombreUsuario(usuario);
    }

    public boolean existeTelefono(String telefono) {
        return usuarioRepositorio.existsByTelefonoUsuario(telefono);
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepositorio.existsByCorreoUsuario(correo);
    }

    public boolean existeRestaurante(String restaurante) {
        return usuarioRepositorio.existsByRestaurante(restaurante);
    }

    /** --- Alias para compatibilidad con controladores --- */
    public boolean usuarioExiste(String usuario) {
        return existeNombreUsuario(usuario);
    }

    public boolean telefonoExiste(String telefono) {
        return existeTelefono(telefono);
    }

    public boolean correoExiste(String correo) {
        return existeCorreo(correo);
    }

    public boolean restauranteExiste(String restaurante) {
        return existeRestaurante(restaurante);
    }

    /** --- Métodos de búsqueda --- */
    public UsuarioModelo buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepositorio.findByNombreUsuario(nombreUsuario).orElse(null);
    }

    public UsuarioModelo buscarPorTelefonoUsuario(String telefonoUsuario) {
        return usuarioRepositorio.findByTelefonoUsuario(telefonoUsuario).orElse(null);
    }

    public UsuarioModelo buscarPorCorreoUsuario(String correoUsuario) {
        return usuarioRepositorio.findByCorreoUsuario(correoUsuario).orElse(null);
    }

    public UsuarioModelo buscarPorRestaurante(String restaurante) {
        return usuarioRepositorio.findByRestaurante(restaurante).orElse(null);
    }

    /** --- Alias de búsqueda para compatibilidad --- */
    public UsuarioModelo buscarPorNombre(String nombreUsuario) {
        return buscarPorNombreUsuario(nombreUsuario);
    }

    /** Exportar usuarios a Excel */
    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<UsuarioModelo> usuarios = usuarioRepositorio.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        String[] columnas = { "ID", "Nombre", "Correo", "Teléfono", "Restaurante", "Rol", "Fecha de Registro" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (UsuarioModelo usuario : usuarios) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(usuario.getIdUsuario());
            row.createCell(1).setCellValue(usuario.getNombreUsuario());
            row.createCell(2).setCellValue(usuario.getCorreoUsuario());
            row.createCell(3).setCellValue(usuario.getTelefonoUsuario());
            row.createCell(4).setCellValue(usuario.getRestaurante() != null ? usuario.getRestaurante() : "");
            row.createCell(5).setCellValue(usuario.getRol() != null ? usuario.getRol().getDescripcion() : "SIN ROL");
            row.createCell(6)
                    .setCellValue(usuario.getFechaRegistro() != null ? usuario.getFechaRegistro().toString() : "");
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String nombreArchivo = "usuarios_" + java.time.LocalDateTime.now().toString().replace(":", "-") + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
