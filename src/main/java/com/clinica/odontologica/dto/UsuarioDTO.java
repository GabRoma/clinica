package com.clinica.odontologica.dto;
import com.clinica.odontologica.entity.UsuarioRol;
import jakarta.validation.constraints.*;
import lombok.Data;
/**
 * La clase `UsuarioDTO` es un Data Transfer Object (DTO) que representa la información de un usuario.
 * Esta clase se utiliza para transferir datos entre las capas de la aplicación.
 * Incluye validaciones para asegurar que los datos sean correctos antes de ser procesados.
 */
@Data
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String username;

    private String password;

    @Email(message = "Debe proporcionar un email válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotNull(message = "Se debe seleccionar un rol")
    private UsuarioRol usuarioRol;

}
