package com.clinica.odontologica.dto;

import com.clinica.odontologica.entity.Domicilio;
import lombok.Data;

@Data
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private Domicilio domicilio;
    private String cedula;
    private String fechaIngreso;
}