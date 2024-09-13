package com.clinica.odontologica.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import java.time.LocalDate;

@Getter
@Setter
@Data
public class TurnoDTO {
    private Long id;
    private LocalDate fecha;
    private Long odontologoId;
    private Long pacienteId;
}