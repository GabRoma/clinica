package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.UsuarioDTO;
import com.clinica.odontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 * Proporciona endpoints para registrar nuevos usuarios y manejar las solicitudes HTTP.
 */
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(UsuarioController.class);

    // Inyección de dependencias
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint para registrar un nuevo usuario.
     * '@param usuarioDTO' Objeto con la información del usuario a registrar.
     * '@return ResponseEntity' con el usuario creado.
     */
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            logger.info("Registrando usuario: " + usuarioDTO.toString());
            // Codificar la contraseña antes de guardarla
            usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            // Guardar el nuevo usuario
            UsuarioDTO nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO);
            return ResponseEntity.status(201).body(nuevoUsuario);  // 201 Created
        } catch (IllegalArgumentException e) {
            // Manejar excepción de solicitud incorrecta
            logger.error("Solicitud incorrecta: " + e.getMessage());
            return ResponseEntity.status(400).build();  // 400 Bad Request
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            logger.error("Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(500).build();  // 500 Internal Server Error
        }
    }
}
