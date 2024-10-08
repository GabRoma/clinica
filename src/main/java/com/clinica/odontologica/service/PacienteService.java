package com.clinica.odontologica.service;

import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.PacienteRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase `PacienteService` proporciona servicios para gestionar pacientes.
 * Incluye métodos para listar, guardar, obtener y eliminar pacientes.
 */
@Service
public class PacienteService {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(PacienteService.class);

    // Inyección de dependencias
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private Mapper mapper;

    // Listar pacientes
    public List<PacienteDTO> listarPacientes() {
        try {
            logger.info("Listando pacientes");
            List<Paciente> pacientes = pacienteRepository.findAll();
            List<PacienteDTO> listaDTO = new ArrayList<>();
            // Convertir cada entidad Paciente a DTO y agregar a la lista
            for(Paciente paciente : pacientes){
                listaDTO.add(mapper.pacienteToDto(paciente));
            }
            return listaDTO;
        } catch (Exception e) {
            logger.error("Error al listar pacientes: " + e.getMessage());
            throw new RuntimeException("Error al listar pacientes", e);
        }
    }

    // Guardar un paciente
    public PacienteDTO guardarPaciente(PacienteDTO pacienteDTO) {
        try {
            logger.info("Guardando paciente: " + pacienteDTO.toString());
            // Convertir el DTO a entidad y guardar en la base de datos
            Paciente paciente = mapper.dtoToPaciente(pacienteDTO);
            Paciente pacienteGuardado = pacienteRepository.save(paciente);
            // Convertir la entidad guardada a DTO y devolver
            return mapper.pacienteToDto(pacienteGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar paciente: " + e.getMessage());
            throw new RuntimeException("Error al guardar paciente", e);
        }
    }

    // Obtener un paciente por su ID
    public PacienteDTO obtenerPaciente(Long id) {
        try {
            logger.info("Obteniendo paciente por ID: " + id);
            // Buscar el paciente por ID, lanzar excepción si no se encuentra
            Paciente paciente = pacienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
            // Convertir la entidad a DTO y devolver
            return mapper.pacienteToDto(paciente);
        } catch (ResourceNotFoundException e) {
            logger.error("Paciente no encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener paciente: " + e.getMessage());
            throw new RuntimeException("Error al obtener paciente", e);
        }
    }

    // Método que obtiene la entidad Paciente directamente por su ID
    public Paciente obtenerEntidadPaciente(Long id) {
        try {
            logger.info("Obteniendo paciente por ID: " + id);
            // Buscar el paciente por ID, lanzar excepción si no se encuentra
            return pacienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        } catch (ResourceNotFoundException e) {
            logger.error("Paciente no encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener paciente: " + e.getMessage());
            throw new RuntimeException("Error al obtener paciente", e);
        }
    }

    // Eliminar un paciente por su ID
    public void eliminarPaciente(Long id) {
        try {
            logger.info("Eliminando paciente por ID: " + id);
            // Verificar si el paciente existe antes de eliminar
            if (!pacienteRepository.existsById(id)) {
                logger.error("Se intentó eliminar un paciente, pero no fue encontrado");
                throw new ResourceNotFoundException("Paciente no encontrado");
            }
            pacienteRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            logger.error("Paciente no encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al eliminar paciente: " + e.getMessage());
            throw new RuntimeException("Error al eliminar paciente", e);
        }
    }
}