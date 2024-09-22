package com.clinica.odontologica.service;

import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.exception.ConflictException;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.TurnoRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La clase `TurnoService` proporciona servicios para gestionar turnos.
 * Incluye métodos para listar, guardar, obtener y eliminar turnos.
 */
@Service
public class TurnoService {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(TurnoService.class);

    // Inyección de dependencias
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private Mapper mapper;

    // Listar turnos
    public List<TurnoDTO> listarTurnos() {
        logger.info("Listando turnos");
        List<Turno> turnos = turnoRepository.findAll();
        // Convertir cada entidad Turno a DTO y devolver la lista
        return turnos.stream()
                .map(turno -> mapper.turnoToDTO(turno))
                .collect(Collectors.toList());
    }

    // Guardar un turno
    public TurnoDTO guardarTurno(TurnoDTO turnoDTO, Paciente paciente, Odontologo odontologo) {
        logger.info("Guardando turno: " + turnoDTO.toString());
        LocalDateTime fechaHora = turnoDTO.getFechaHora();
        // Verificar si el odontólogo ya tiene un turno asignado en la misma fecha y hora
        if (turnoRepository.existsByOdontologoAndFechaHora(odontologo, fechaHora)) {
            logger.error("El odontólogo ya tiene un turno asignado en esta fecha y hora.");
            throw new ConflictException("El odontólogo ya tiene un turno asignado en esta fecha y hora.");
        }
        // Convertir el DTO a entidad, establecer paciente y odontólogo, y guardar en la base de datos
        Turno turno = mapper.dtoToTurno(turnoDTO);
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        Turno nuevoTurno = turnoRepository.save(turno);
        // Convertir la entidad guardada a DTO y devolver
        return mapper.turnoToDTO(nuevoTurno);
    }

    // Obtener un turno por su ID
    public TurnoDTO obtenerTurno(Long id) {
        logger.info("Obteniendo turno por ID: " + id);
        // Buscar el turno por ID, lanzar excepción si no se encuentra
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        // Convertir la entidad a DTO y devolver
        return mapper.turnoToDTO(turno);
    }

    // Eliminar un turno por su ID
    public void eliminarTurno(Long id) {
        logger.info("Eliminando turno por ID: " + id);
        // Verificar si el turno existe antes de eliminar
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
        turnoRepository.deleteById(id);
    }
}