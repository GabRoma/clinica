package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.service.TurnoService;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
/**
 * Controlador REST para gestionar los turnos en la clínica odontológica.
 * Proporciona endpoints para listar, agregar, obtener y eliminar turnos.
 */
@RestController
@RequestMapping("/turnos")
@Validated
public class TurnoController {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(TurnoController.class);

    // Inyección de dependencias
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private Mapper mapper;

    /**
     * Endpoint para listar todos los turnos.
     * '@return ResponseEntity' con la lista de turnos.
     */
    @Operation(summary = "Listar turnos", description = "Devuelve una lista de todos los turnos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de turnos obtenida correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TurnoDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        try {
            logger.info("Listando turnos");
            List<TurnoDTO> turnos = turnoService.listarTurnos();
            return ResponseEntity.ok(turnos);
        } catch (Exception e) {
            logger.error("Error al listar turnos: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Endpoint para agregar un nuevo turno.
     * '@param turnoDTO' Objeto con la información del turno a agregar.
     * '@return ResponseEntity' con el turno creado.
     */
    @Operation(summary = "Agregar un nuevo turno", description = "Crea un nuevo turno en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno creado exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TurnoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/agregar")
    public ResponseEntity<TurnoDTO> agregar(@RequestBody TurnoDTO turnoDTO) {
        try {
            logger.info("Agregando turno: " + turnoDTO.toString());
            // Obtener entidades de Paciente y Odontologo a partir de sus IDs
            Paciente paciente = pacienteService.obtenerEntidadPaciente(turnoDTO.getPaciente().getId());
            Odontologo odontologo = odontologoService.obtenerEntidadOdontologo(turnoDTO.getOdontologo().getId());
            if (paciente != null && odontologo != null) {
                // Guardar el nuevo turno
                TurnoDTO nuevoTurno = turnoService.guardarTurno(turnoDTO, paciente, odontologo);
                return ResponseEntity.status(201).body(nuevoTurno);  // 201 Created
            } else {
                return ResponseEntity.status(400).build();  // 400 Bad Request
            }
        } catch (Exception e) {
            logger.error("Error al agregar turno: " + e.getMessage());
            return ResponseEntity.status(500).body(null);  // 500 Internal Server Error
        }
    }

    /**
     * Endpoint para obtener un turno por su ID.
     * '@param id' ID del turno a obtener.
     * '@return ResponseEntity' con el turno obtenido.
     */
    @Operation(summary = "Obtener un turno", description = "Obtiene un turno por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno obtenido correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TurnoDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> obtener(@PathVariable Long id) {
        try {
            logger.info("Obteniendo turno por ID: " + id);
            TurnoDTO turno = turnoService.obtenerTurno(id);
            return ResponseEntity.ok(turno);  // 200 OK
        } catch (ResourceNotFoundException e) {
            logger.error("Turno no encontrado: " + e.getMessage());
            return ResponseEntity.status(404).body(null);  // 404 Not Found
        } catch (Exception e) {
            logger.error("Error al obtener turno: " + e.getMessage());
            return ResponseEntity.status(500).body(null);  // 500 Internal Server Error
        }
    }

    /**
     * Endpoint para eliminar un turno por su ID.
     * '@param id' ID del turno a eliminar.
     * '@return ResponseEntity' sin contenido si la eliminación es exitosa.
     */
    @Operation(summary = "Eliminar un turno", description = "Elimina un turno por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turno eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            logger.info("Eliminando turno por ID: " + id);
            turnoService.eliminarTurno(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (ResourceNotFoundException e) {
            logger.error("Turno no encontrado: " + e.getMessage());
            return ResponseEntity.status(404).build();  // 404 Not Found
        } catch (Exception e) {
            logger.error("Error al eliminar turno: " + e.getMessage());
            return ResponseEntity.status(500).build();  // 500 Internal Server Error
        }
    }
}
