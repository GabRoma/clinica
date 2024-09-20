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

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    // Login: crear el token de autenticación
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
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


