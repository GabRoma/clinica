package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/pacientes")
@Validated
public class PacienteController {
    private static final Logger logger = LogManager.getLogger(PacienteController.class);

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private Mapper mapper;

    // Endpoint para listar pacientes
    @Operation(summary = "Listar todos los pacientes", description = "Devuelve una lista de todos los pacientes registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listar(){
        try {
            logger.info("Se ha solicitado la lista de pacientes");
            List<PacienteDTO> pacientes = pacienteService.listarPacientes();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            logger.error("Error al listar pacientes: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para agregar un paciente
    @Operation(summary = "Agregar un nuevo paciente", description = "Crea un nuevo paciente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/agregar")
    public ResponseEntity<PacienteDTO> agregar(@RequestBody PacienteDTO pacienteDTO) {
        try {
            logger.info("Agregando paciente: " + pacienteDTO.toString());
            PacienteDTO nuevoPaciente = pacienteService.guardarPaciente(pacienteDTO);
            return ResponseEntity.status(201).body(nuevoPaciente);  // 201 Created
        } catch (Exception e) {
            logger.error("Error al agregar paciente: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para obtener un paciente por su ID
    @Operation(summary = "Obtener un paciente", description = "Obtiene un paciente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente obtenido correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PacienteDTO> obtener(@PathVariable Long id) {
        try {
            logger.info("Obteniendo paciente por ID: " + id);
            PacienteDTO paciente = pacienteService.obtenerPaciente(id);
            return ResponseEntity.ok(paciente);  // 200 OK
        } catch (Exception e) {
            logger.error("Error al obtener paciente: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para eliminar un paciente por su ID
    @Operation(summary = "Eliminar un paciente", description = "Elimina un paciente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            logger.info("Eliminando paciente por ID: " + id);
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (Exception e) {
            logger.error("Error al eliminar paciente: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
