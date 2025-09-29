package com.example.web.jams.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.*;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioModelo {

    public UsuarioModelo(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4,  message = "La contraseña debe tener entre 4 y 40 caracteres")
    @Column(name = "psw_usuario", nullable = false)
    private String pswUsuario;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato valido")
    @Column(name = "correo_usuario", nullable = false, unique = true, length = 50)
    private String correoUsuario;

    @NotBlank(message = "El telefono es obligatorio")   
    @Pattern(regexp = "^3\\d{9}$", message = "El número debe tener 10 dígitos y comenzar con 3")
    @Column(name = "telefono_usuario", nullable = false, unique = true, length = 10)
    private String telefonoUsuario;

    @Column(name = "restaurante", unique = true, nullable = true, length = 30)
    private String restaurante;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // Relación con SuscripcionUsuario
    @OneToMany(mappedBy = "usuario")
    private List<SuscripcionUsuarioModelo> suscripciones;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RolModelo rol;

    @PrePersist
    @PreUpdate
    public void limpiarCampos() {
        if (restaurante != null && restaurante.isEmpty()) {
            restaurante = null;
        }
    }
}
