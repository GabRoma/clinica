package com.clinica.odontologica.entity;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
/**
 * La clase `Odontologo` representa la entidad de un odontólogo en la base de datos.
 * Utiliza anotaciones de JPA para mapear sus campos a una tabla de la base de datos.
 * Incluye atributos como nombre, apellido y matrícula, con validaciones para asegurar que los datos sean correctos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(nullable = false)
    private String apellido;

    @NotBlank(message = "La matrícula no puede estar vacía")
    @Pattern(regexp = "\\d{6}", message = "La matrícula debe tener 6 dígitos")
    @Column(nullable = false, unique = true)
    private String matricula;

    public Odontologo(String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }
}