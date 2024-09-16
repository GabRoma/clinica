package com.clinica.odontologica.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class TurnoDTO {
    private Long id;

    @NotNull(message = "La fecha del turno no puede estar vacía")
    @FutureOrPresent(message = "La fecha del turno debe ser hoy o una fecha futura")
    private LocalDateTime fechaHora;

    @NotNull(message = "El ID del odontólogo no puede ser nulo")
    private Long odontologoId;

    @NotNull(message = "El ID del paciente no puede ser nulo")
    private Long pacienteId;
}