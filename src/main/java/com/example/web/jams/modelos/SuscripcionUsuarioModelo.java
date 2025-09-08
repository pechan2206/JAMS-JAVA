package com.example.web.jams.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "suscripcion_usuario")
public class SuscripcionUsuarioModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario", nullable = false)
    private UsuarioModelo usuario;

    @ManyToOne
    @JoinColumn(name = "fk_id_suscripcion", nullable = false)
    private SuscripcionModelo suscripcion;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;
}
