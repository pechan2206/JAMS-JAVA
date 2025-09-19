package com.example.web.jams.servicios;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.web.jams.modelos.UsuarioModelo;
import com.example.web.jams.repositorios.SuscripcionUsuarioRepositorio;
import com.example.web.jams.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio,
            SuscripcionUsuarioRepositorio suscripcionUsuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.suscripcionUsuarioRepositorio = suscripcionUsuarioRepositorio;
    }

    public List<UsuarioModelo> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public UsuarioModelo guardarUsuario(UsuarioModelo usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public UsuarioModelo buscarPorId(Long id) {
        return usuarioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el id" + id));
    }

    @Transactional
    public void eliminar(Long id) {
        suscripcionUsuarioRepositorio.deleteByUsuarioId(id);
        usuarioRepositorio.deleteById(id);
    }

    public UsuarioModelo actualizar(Long id, UsuarioModelo datos) {
        UsuarioModelo existente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ✅ Actualizar solo los campos que vienen del form
        existente.setNombreUsuario(datos.getNombreUsuario());
        existente.setCorreoUsuario(datos.getCorreoUsuario());
        existente.setTelefonoUsuario(datos.getTelefonoUsuario());
        existente.setRestaurante(datos.getRestaurante());

        return usuarioRepositorio.save(existente);
    }

    public void exportarExcel(HttpServletResponse response) throws IOException {
        List<UsuarioModelo> usuarios = usuarioRepositorio.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        // Estilo de encabezado
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        // Cabecera
        String[] columnas = { "ID", "Nombre", "Correo", "Teléfono", "Restaurante", "Rol", "Fecha de Registro" };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerStyle);
        }

        // Datos
        int rowIdx = 1;
        for (UsuarioModelo usuario : usuarios) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(usuario.getIdUsuario());
            row.createCell(1).setCellValue(usuario.getNombreUsuario());
            row.createCell(2).setCellValue(usuario.getCorreoUsuario());
            row.createCell(3).setCellValue(usuario.getTelefonoUsuario());
            row.createCell(4).setCellValue(usuario.getRestaurante());
            row.createCell(5).setCellValue(usuario.getRol().getIdRol());
            row.createCell(6).setCellValue(usuario.getFechaRegistro().toString());
        }

        // Autoajustar columnas
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Configuración para descargar
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String nombreArchivo = "usuarios_" + java.time.LocalDateTime.now().toString().replace(":", "-") + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + nombreArchivo);

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
