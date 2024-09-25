package com.clinica.odontologica.utils;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.dto.UsuarioDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.entity.Usuario;
import org.springframework.stereotype.Component;

/**
 * La clase `Mapper` proporciona métodos para convertir entidades a DTOs y viceversa.
 * Esto es útil para separar la lógica de negocio de la lógica de presentación.
 */
@Component
public class Mapper {

    // Métodos para convertir entre Odontologo y OdontologoDTO

    /**
     * Convierte una entidad `Odontologo` a un `OdontologoDTO`.
     * param odontologo La entidad `Odontologo` a convertir.
     * return El `OdontologoDTO` resultante.
     */
    public OdontologoDTO odontologoToDto(Odontologo odontologo) {
        OdontologoDTO dto = new OdontologoDTO();
        dto.setId(odontologo.getId());
        dto.setNombre(odontologo.getNombre());
        dto.setApellido(odontologo.getApellido());
        dto.setMatricula(odontologo.getMatricula());
        return dto;
    }

    /**
     * Convierte un `OdontologoDTO` a una entidad `Odontologo`.
     * param dto El `OdontologoDTO` a convertir.
     * return La entidad `Odontologo` resultante.
     */
    public Odontologo dtoToOdontologo(OdontologoDTO dto) {
        Odontologo odontologo = new Odontologo();
        odontologo.setId(dto.getId());
        odontologo.setNombre(dto.getNombre());
        odontologo.setApellido(dto.getApellido());
        odontologo.setMatricula(dto.getMatricula());
        return odontologo;
    }

    // Métodos para convertir entre Paciente y PacienteDTO

    /**
     * Convierte una entidad `Paciente` a un `PacienteDTO`.
     * param paciente La entidad `Paciente` a convertir.
     * return El `PacienteDTO` resultante.
     */
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

    /**
     * Convierte un `PacienteDTO` a una entidad `Paciente`.
     * param dto El `PacienteDTO` a convertir.
     * return La entidad `Paciente` resultante.
     */
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

    // Métodos para convertir entre Turno y TurnoDTO

    /**
     * Convierte una entidad `Turno` a un `TurnoDTO`.
     * param turno La entidad `Turno` a convertir.
     * return El `TurnoDTO` resultante.
     */
    public TurnoDTO turnoToDTO(Turno turno) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId(turno.getId());
        dto.setFechaHora(turno.getFechaHora());
        dto.setPaciente(pacienteToDto(turno.getPaciente()));
        dto.setOdontologo(odontologoToDto(turno.getOdontologo()));
        return dto;
    }

    /**
     * Convierte un `TurnoDTO` a una entidad `Turno`.
     * param dto El `TurnoDTO` a convertir.
     * return La entidad `Turno` resultante.
     */
    public Turno dtoToTurno(TurnoDTO dto) {
        Turno turno = new Turno();
        turno.setId(dto.getId());
        turno.setFechaHora(dto.getFechaHora());
        turno.setPaciente(dtoToPaciente(dto.getPaciente()));
        turno.setOdontologo(dtoToOdontologo(dto.getOdontologo()));
        return turno;
    }

    // Métodos para convertir entre Usuario y UsuarioDTO

    /**
     * Convierte una entidad `Usuario` a un `UsuarioDTO`.
     * param usuario La entidad `Usuario` a convertir.
     * return El `UsuarioDTO` resultante.
     */
    public UsuarioDTO usuarioToDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setUsuarioRol(usuario.getUsuarioRol());
        return dto;
    }

    /**
     * Convierte un `UsuarioDTO` a una entidad `Usuario`.
     * param dto El `UsuarioDTO` a convertir.
     * return La entidad `Usuario` resultante.
     */
    public Usuario dtoToUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setPassword(dto.getPassword());
        usuario.setEmail(dto.getEmail());
        usuario.setUsuarioRol(dto.getUsuarioRol());
        return usuario;
    }
}