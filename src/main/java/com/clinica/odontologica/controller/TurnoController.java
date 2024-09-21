package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Odontologo;
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

@RestController
@RequestMapping("/turnos")
@Validated
public class TurnoController {

    private static final Logger logger = LogManager.getLogger(TurnoController.class);

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private Mapper mapper;

    // Endpoint para listar turnos
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
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para agregar un turno
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
            Paciente paciente = pacienteService.obtenerEntidadPaciente(turnoDTO.getPaciente().getId());
            Odontologo odontologo = odontologoService.obtenerEntidadOdontologo(turnoDTO.getOdontologo().getId());
            if (paciente != null && odontologo != null) {
                TurnoDTO nuevoTurno = turnoService.guardarTurno(turnoDTO, paciente, odontologo);
                return ResponseEntity.status(201).body(nuevoTurno);  // 201 Created
            } else {
                return ResponseEntity.badRequest().build();  // 400 Bad Request
            }
        } catch (Exception e) {
            logger.error("Error al agregar turno: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para obtener un turno por su ID
    @Operation(summary = "Obtener un turno", description = "Obtiene un turno por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno obtenido correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TurnoDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDTO> obtener(@PathVariable Long id) {
        try {
            logger.info("Obteniendo turno por ID: " + id);
            TurnoDTO turno = turnoService.obtenerTurno(id);
            return ResponseEntity.ok(turno);  // 200 OK
        } catch (Exception e) {
            logger.error("Error al obtener turno: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint para eliminar un turno por su ID
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
        } catch (Exception e) {
            logger.error("Error al eliminar turno: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
