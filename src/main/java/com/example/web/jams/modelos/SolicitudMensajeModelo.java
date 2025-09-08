package com.example.web.jams.modelos;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "solicitud_mensaje")
public class SolicitudMensajeModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long idSolicitud;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(
            name = "estado",
            nullable = false,
            columnDefinition = "ENUM('pendiente','validado') DEFAULT 'pendiente'"
    )
    private String estado = "pendiente";
}
