package com.clinica.odontologica.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.dto.OdontologoDTO;
/**
 * La clase `TurnoDTO` es un Data Transfer Object (DTO) que representa la información de un turno.
 * Esta clase se utiliza para transferir datos entre las capas de la aplicación.
 * Incluye validaciones para asegurar que los datos sean correctos antes de ser procesados.
 */
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