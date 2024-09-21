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


@RestController
@RequestMapping
public class AuthController {

    private static final Logger logger = LogManager.getLogger(PacienteController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    // Login: crear el token de autenticación
    @Operation(summary = "Autenticar un usuario", description = "Autentica un usuario en el sistema y devuelve un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado correctamente",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Usuario o contraseña incorrectos", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acceso denegado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        logger.info("Autenticando usuario: " + authenticationRequest.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            logger.info("Usuario autenticado: " + authenticationRequest.getUsername());
        } catch (BadCredentialsException e) {
            logger.error("Usuario o contraseña incorrectos. Error: " + e);
            throw new Exception("Usuario o contraseña incorrectos", e);
        }

        // Cargar los detalles del usuario autenticado
        final UserDetails userDetails = usuarioService.loadUserByUsername(authenticationRequest.getUsername());
        final String role = userDetails.getAuthorities().iterator().next().getAuthority();  // Extraer el rol del usuario

        // Generar el token JWT con el username y el rol
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), role);

        // Retornar el token en la respuesta
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}


