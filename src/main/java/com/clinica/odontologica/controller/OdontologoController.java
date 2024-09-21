package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.service.OdontologoService;
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
/**
 * OdontologoController es una clase que maneja las operaciones relacionadas con los odontólogos en el sistema.
 * Proporciona endpoints para listar, agregar, obtener y eliminar odontólogos.
 */
@RestController
@RequestMapping("/odontologos")
@Validated
public class OdontologoController {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(OdontologoController.class);

    // Inyección de dependencias
    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private Mapper mapper;

    /**
     * Endpoint para listar todos los odontólogos.
     * '@return ResponseEntity' con la lista de odontólogos.
     */
    @Operation(summary = "Listar odontólogos", description = "Devuelve una lista de todos los odontólogos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de odontólogos obtenida correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listar() {
        try {
            logger.info("Listando odontólogos");
            List<OdontologoDTO> odontologos = odontologoService.listarOdontologos();
            return ResponseEntity.ok(odontologos);
        } catch (Exception e) {
            logger.error("Error al listar odontólogos: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Endpoint para agregar un nuevo odontólogo.
     * '@param odontologoDTO' Objeto con la información del odontólogo a agregar.
     * '@return ResponseEntity' con el odontólogo creado.
     */
    @Operation(summary = "Agregar un nuevo odontólogo", description = "Crea un nuevo odontólogo en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontólogo creado exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/agregar")
    public ResponseEntity<OdontologoDTO> agregar(@RequestBody OdontologoDTO odontologoDTO) {
        try {
            logger.info("Agregando odontólogo: " + odontologoDTO.toString());
            OdontologoDTO nuevoOdontologo = odontologoService.guardarOdontologo(odontologoDTO);
            return ResponseEntity.status(201).body(nuevoOdontologo);  // 201 Created
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                logger.error("Solicitud incorrecta al agregar odontólogo: " + e.getMessage());
                return ResponseEntity.status(400).build();  // 400 Bad Request
            } else {
                logger.error("Error al agregar odontólogo: " + e.getMessage());
                return ResponseEntity.status(500).build();  // 500 Internal Server Error
            }
        }
    }

    /**
     * Endpoint para obtener un odontólogo por su ID.
     * '@param id' ID del odontólogo a obtener.
     * '@return ResponseEntity' con el odontólogo obtenido.
     */
    @Operation(summary = "Obtener un odontólogo", description = "Obtiene un odontólogo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontólogo obtenido correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoDTO> obtener(@PathVariable Long id) {
        try {
            logger.info("Obteniendo odontólogo por ID: " + id);
            OdontologoDTO odontologo = odontologoService.obtenerOdontologo(id);
            return ResponseEntity.ok(odontologo);  // 200 OK
        } catch (Exception e) {
            logger.error("Error al obtener odontólogo: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Endpoint para eliminar un odontólogo por su ID.
     * '@param id' ID del odontólogo a eliminar.
     * '@return ResponseEntity' sin contenido si la eliminación es exitosa.
     */
    @Operation(summary = "Eliminar un odontólogo", description = "Elimina un odontólogo por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Odontólogo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            logger.info("Eliminando odontólogo por ID: " + id);
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.noContent().build();  // 204 No Content
        } catch (ResourceNotFoundException e) {
            logger.error("Odontólogo no encontrado: " + e.getMessage());
            return ResponseEntity.status(404).build();  // 404 Not Found
        } catch (Exception e) {
            logger.error("Error al eliminar odontólogo: " + e.getMessage());
            return ResponseEntity.status(500).build();  // 500 Internal Server Error
        }
    }
}