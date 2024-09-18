package com.clinica.odontologica.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.dto.OdontologoDTO;

@Getter
@Setter
@Data
public class TurnoDTO {
    private Long id;

    @NotNull(message = "La fecha del turno no puede estar vacía")
    @FutureOrPresent(message = "La fecha del turno debe ser hoy o una fecha futura")
    private LocalDateTime fechaHora;

    @NotNull(message = "Se debe seleccionar un odontólogo")
    private OdontologoDTO odontologo;

    @NotNull(message = "Se debe seleccionar un paciente")
    private PacienteDTO paciente;
}