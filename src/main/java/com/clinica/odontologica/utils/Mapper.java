package com.clinica.odontologica.utils;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Turno;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    // Odontólogo
    public OdontologoDTO odontologoToDto(Odontologo odontologo) {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setId(odontologo.getId());
        dto.setNombre(odontologo.getNombre());
        dto.setApellido(odontologo.getApellido());
        dto.setMatricula(odontologo.getMatricula());
        return dto;
    }

    public Odontologo dtoToOdontologo(OdontologoDTO dto) {
        Odontologo odontologo = new Odontologo();
        odontologo.setId(dto.getId());
        odontologo.setNombre(dto.getNombre());
        odontologo.setApellido(dto.getApellido());
        odontologo.setMatricula(dto.getMatricula());
        return odontologo;
    }

    // Paciente
    public PacienteDTO pacienteToDto(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombre(paciente.getNombre());
        dto.setApellido(paciente.getApellido());
        dto.setDomicilio(paciente.getDomicilio());
        dto.setCedula(paciente.getCedula());
        dto.setFechaIngreso(paciente.getFechaIngreso());
        dto.setEmail(paciente.getEmail());
        return dto;
    }

    public Paciente dtoToPaciente(PacienteDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setNombre(dto.getNombre());
        paciente.setApellido(dto.getApellido());
        paciente.setDomicilio(dto.getDomicilio());
        paciente.setCedula(dto.getCedula());
        paciente.setFechaIngreso(dto.getFechaIngreso());
        paciente.setEmail(dto.getEmail());
        return paciente;
    }

    // Turno
    public TurnoDTO turnoToDTO(Turno turno) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId(turno.getId());
        dto.setOdontologoId(turno.getOdontologo().getId());
        dto.setPacienteId(turno.getPaciente().getId());
        dto.setFechaHora(turno.getFechaHora());
        return dto;
    }

    public Turno dtoToTurno(TurnoDTO dto) {
        Turno turno = new Turno();
        turno.setId(dto.getId());
        turno.setFechaHora(dto.getFechaHora());
        return turno;
    }
}
