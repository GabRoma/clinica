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


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger logger = LogManager.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            UsuarioDTO nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO);
            return ResponseEntity.status(201).body(nuevoUsuario);
        } catch (Exception e) {
            logger.error("Error al registrar usuario: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
