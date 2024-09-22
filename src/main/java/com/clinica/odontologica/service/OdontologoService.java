package com.clinica.odontologica.service;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.OdontologoRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La clase `OdontologoService` proporciona servicios para gestionar odontólogos.
 * Incluye métodos para listar, guardar, obtener y eliminar odontólogos.
 */
@Service
public class OdontologoService {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(OdontologoService.class);

    // Inyección de dependencias
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private Mapper mapper;

    // Listar odontólogos
    public List<OdontologoDTO> listarOdontologos() {
        try {
            logger.info("Listando odontólogos");
            List<Odontologo> listaOdontologos = odontologoRepository.findAll();
            List<OdontologoDTO> listaDTO = new ArrayList<>();
            // Convertir cada entidad Odontologo a DTO y agregar a la lista
            for(Odontologo odontologo : listaOdontologos){
                listaDTO.add(mapper.odontologoToDto(odontologo));
            }
            return listaDTO;
        } catch (Exception e) {
            logger.error("Error al listar odontólogos: " + e.getMessage());
            throw new RuntimeException("Error al listar odontólogos", e);
        }
    }

    // Guardar un odontólogo
    public OdontologoDTO guardarOdontologo(OdontologoDTO odontologoDTO) {
        try {
            logger.info("Guardando odontólogo: " + odontologoDTO.toString());
            // Convertir el DTO a entidad y guardar en la base de datos
            Odontologo odontologo = mapper.dtoToOdontologo(odontologoDTO);
            Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
            // Convertir la entidad guardada a DTO y devolver
            return mapper.odontologoToDto(odontologoGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar odontólogo: " + e.getMessage());
            throw new RuntimeException("Error al guardar odontólogo", e);
        }
    }

    // Obtener un odontólogo por su ID
    public OdontologoDTO obtenerOdontologo(Long id) {
        try {
            logger.info("Obteniendo odontólogo por ID: " + id);
            // Buscar el odontólogo por ID, lanzar excepción si no se encuentra
            Odontologo odontologo = odontologoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado"));
            // Convertir la entidad a DTO y devolver
            return mapper.odontologoToDto(odontologo);
        } catch (ResourceNotFoundException e) {
            logger.error("Odontólogo no encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener odontólogo: " + e.getMessage());
            throw new RuntimeException("Error al obtener odontólogo", e);
        }
    }

    // Método que obtiene la entidad Odontologo directamente por su ID
    public Odontologo obtenerEntidadOdontologo(Long id) {
        try {
            logger.info("Obteniendo odontólogo por ID: " + id);
            // Buscar el odontólogo por ID, lanzar excepción si no se encuentra
            return odontologoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado"));
        } catch (ResourceNotFoundException e) {
            logger.error("Odontólogo no encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al obtener odontólogo: " + e.getMessage());
            throw new RuntimeException("Error al obtener odontólogo", e);
        }
    }

    // Eliminar un odontólogo por su ID
    public void eliminarOdontologo(Long id) {
        try {
            logger.info("Eliminando odontólogo por ID: " + id);
            // Verificar si el odontólogo existe antes de eliminar
            if (!odontologoRepository.existsById(id)) {
                logger.error("Se intentó eliminar un odontólogo, pero no fue encontrado");
                throw new ResourceNotFoundException("Odontólogo no encontrado");
            }
            odontologoRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            logger.error("Odontólogo no encontrado: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error al eliminar odontólogo: " + e.getMessage());
            throw new RuntimeException("Error al eliminar odontólogo", e);
        }
    }
}