package com.clinica.odontologica.entity;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
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
    @ManyToOne
    @JoinColumn(name = "paciente_id",referencedColumnName = "id")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "odontologo_id",referencedColumnName = "id")
    private Odontologo odontologo;
    private LocalDate fecha;

    public Turno(Paciente paciente, Odontologo odontologo, LocalDate fecha) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
    }
}