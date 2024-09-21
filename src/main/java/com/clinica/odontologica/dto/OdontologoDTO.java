package com.clinica.odontologica.dto;
import lombok.Data;
import jakarta.validation.constraints.*;

/**
 * OdontologoDTO es una clase de transferencia de datos que representa a un odontólogo.
 * Contiene validaciones para asegurar que los datos sean correctos.
 */
@Data
public class OdontologoDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotBlank(message = "La matrícula no puede estar vacía")
    @Pattern(regexp = "\\d{6}", message = "La matrícula debe tener 6 dígitos")
    private String matricula;
}