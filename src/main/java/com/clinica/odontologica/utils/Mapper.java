package com.clinica.odontologica.utils;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.dto.TurnoDTO;
import org.springframework.stereotype.Component;
@Component
public class Mapper {
    public OdontologoDTO odontologoToDto(Odontologo odontologo) {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setId(odontologo.getId());
        dto.setNombre(odontologo.getNombre());
        dto.setApellido(odontologo.getApellido());
        dto.setMatricula(odontologo.getMatricula());
        return dto;
    }
    public PacienteDTO pacienteToDto(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setDomicilio(paciente.getDomicilio());
        dto.setCedula(paciente.getCedula());
        dto.setFechaIngreso(paciente.getFechaIngreso().toString());
        return dto;
    }
    public TurnoDTO turnoToDTO(Turno turno){
        TurnoDTO dto= new TurnoDTO();
        dto.setId(turno.getId());
        dto.setOdontologoId(turno.getOdontologo().getId());
        dto.setPacienteId(turno.getPaciente().getId());
        dto.setFecha(turno.getFecha());
        return dto;
    }
}
