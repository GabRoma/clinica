package com.clinica.odontologica.entity;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
/**
 * La clase `Turno` representa la entidad de un turno en la base de datos.
 * Utiliza anotaciones de JPA para mapear sus campos a una tabla de la base de datos.
 * Incluye atributos como paciente, odontólogo y fecha del turno, con validaciones
 * para asegurar que los datos sean correctos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El paciente no puede ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id",referencedColumnName = "id")
    private Paciente paciente;

    @NotNull(message = "El odontólogo no puede ser nulo")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "odontologo_id",referencedColumnName = "id")
    private Odontologo odontologo;

    @NotNull(message = "La fecha del turno no puede ser nula")
    @FutureOrPresent(message = "La fecha del turno debe ser hoy o una fecha futura")
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    public Turno(Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }
}