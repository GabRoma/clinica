package com.clinica.odontologica.entity;
import lombok.*;
import jakarta.persistence.*;
/**
 * La clase `Domicilio` representa la entidad de un domicilio en la base de datos.
 * Utiliza anotaciones de JPA para mapear sus campos a una tabla de la base de datos.
 * Incluye atributos como calle, número, localidad y provincia.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String calle;
    @Column
    private Integer numero;
    @Column
    private String localidad;
    @Column
    private String provincia;

    public Domicilio(String calle, Integer numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }
}