package com.example.web.jams.dto;

import java.time.LocalDateTime;

public interface SuscripcionesPorUsuarioDTO {

    String getDescripcion();

    Double getPrecio();

    LocalDateTime getFechaModificacion();

    LocalDateTime getFechaCompra();

}
