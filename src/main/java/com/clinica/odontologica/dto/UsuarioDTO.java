package com.clinica.odontologica.dto;
import com.clinica.odontologica.entity.UsuarioRol;
import jakarta.validation.constraints.*;
import lombok.Data;
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
