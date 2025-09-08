package com.example.web.jams.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "suscripcion")
public class SuscripcionModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suscripcion")
    private Long idSuscripcion;

    @Column(name = "descripcion", nullable = false, length = 40)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Relación con SuscripcionUsuario
    @OneToMany(mappedBy = "suscripcion")
    private List<SuscripcionUsuarioModelo> usuarios;

    
}
