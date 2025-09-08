package com.example.web.jams.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioModelo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 30)
    private String nombreUsuario;

    @Column(name = "psw_usuario", nullable = false, length = 40)
    private String pswUsuario;

    @Column(name = "correo_usuario", nullable = false, unique = true, length = 50)
    private String correoUsuario;

    @Column(name = "telefono_usuario", nullable = false, unique = true, length = 10)
    private String telefonoUsuario;

    @Column(name = "restaurante", unique = true, length = 30)
    private String restaurante;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Relación con SuscripcionUsuario
    @OneToMany(mappedBy = "usuario")
    private List<SuscripcionUsuarioModelo> suscripciones;

    @ManyToOne
    @JoinColumn(name = "id_rol" , nullable = false)
    private RolModelo rol;
}
