package com.example.web.jams.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private String idUsuario;


    @NotBlank(message = "El mensaje no puede estar vacio")
    @Column(name = "mensaje", nullable = false, length = 200)
    private String mensaje;

    @NotBlank(message = "El asunto no puede estar vacio")
    @Column(name = "asunto", nullable = false)
    private String asunto;

    @Column(
            name = "estado",
            nullable = false,
            columnDefinition = "ENUM('pendiente','validado') DEFAULT 'pendiente'"
    )
    private String estado = "pendiente";
}
