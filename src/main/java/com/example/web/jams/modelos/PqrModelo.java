package com.example.web.jams.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "pqr")
public class PqrModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pqr")
    private Long idPqr;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "mensaje", nullable = false, length = 200)
    private String mensaje;

    @Column(name = "asunto", nullable = false)
    private String asunto;

    @Column(
            name = "estado",
            nullable = false,
            columnDefinition = "ENUM('pendiente','validado') DEFAULT 'pendiente'"
    )
    private String estado = "pendiente";
}
