package com.example.web.jams.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
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

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, max = 40, message = "La descripcion debe de ser mas larga de 5")
    @Column(name = "descripcion", nullable = false, length = 40)
    private String descripcion;


    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    @Digits(integer = 6, fraction = 2, message = "El precio debe tener máximo 6 enteros y 2 decimales")
    @DecimalMin(value = "10000.0", inclusive = true, message = "El valor debe ser mayor o igual a 10000")
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // Relación con SuscripcionUsuario
    @OneToMany(mappedBy = "suscripcion")
    private List<SuscripcionUsuarioModelo> usuarios;

}
