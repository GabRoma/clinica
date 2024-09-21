package com.clinica.odontologica.controller;

import com.clinica.odontologica.authentication.AuthenticationRequest;
import com.clinica.odontologica.authentication.AuthenticationResponse;
import com.clinica.odontologica.utils.JwtUtil;
import com.clinica.odontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
/**
 * AuthController es una clase que maneja la autenticación de usuarios en el sistema.
 * Proporciona un endpoint para autenticar usuarios y generar tokens JWT.
 */
@RestController
@RequestMapping
public class AuthController {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(PacienteController.class);

    // Inyección de dependencias
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para autenticar un usuario y generar un token JWT.
     * '@param authenticationRequest' Objeto que contiene el nombre de usuario y la contraseña.
     * '@return ResponseEntity' con el token JWT si la autenticación es exitosa.
     */

    @Operation(summary = "Autenticar un usuario", description = "Autentica un usuario en el sistema y devuelve un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Usuario o contraseña incorrectos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        logger.info("Autenticando usuario: " + authenticationRequest.getUsername());
        try {
            // Autenticar al usuario con el nombre de usuario y la contraseña proporcionados
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            logger.info("Usuario autenticado: " + authenticationRequest.getUsername());
        } catch (BadCredentialsException e) {
            // Manejar excepción de credenciales incorrectas
            logger.error("Usuario o contraseña incorrectos. Error: " + e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos", e);
        } catch (Exception e) {
            // Manejar cualquier otra excepción durante la autenticación
            logger.error("Error interno del servidor. Error: " + e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", e);
        }

        try {
            // Cargar los detalles del usuario autenticado
            final UserDetails userDetails = usuarioService.loadUserByUsername(authenticationRequest.getUsername());
            final String role = userDetails.getAuthorities().iterator().next().getAuthority();  // Extraer el rol del usuario

            // Generar el token JWT con el nombre de usuario y el rol
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);

            // Retornar el token en la respuesta
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (Exception e) {
            // Manejar excepción de acceso denegado
            logger.error("Acceso denegado. Error: " + e);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado", e);
        }
    }
}
