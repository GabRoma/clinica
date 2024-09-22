package com.clinica.odontologica.dto;
import com.clinica.odontologica.entity.Domicilio;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
/**
 * La clase `PacienteDTO` es un Data Transfer Object (DTO) que representa la información de un paciente.
 * Esta clase se utiliza para transferir datos entre las capas de la aplicación.
 * Incluye validaciones para asegurar que los datos sean correctos antes de ser procesados.
 */
@Data
public class PacienteDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "La cédula no puede estar vacía")
    @Pattern(regexp = "\\d{8}", message = "La cédula debe tener 8 dígitos")
    private String cedula;

    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    private LocalDate fechaIngreso;

    @Email(message = "Debe proporcionar un email válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotNull(message = "El domicilio no puede estar vacío")
    private Domicilio domicilio;
}