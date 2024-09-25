package com.clinica.odontologica.Tests;

import com.clinica.odontologica.controller.AuthController;
import com.clinica.odontologica.authentication.AuthenticationRequest;
import com.clinica.odontologica.authentication.AuthenticationResponse;
import com.clinica.odontologica.utils.JwtUtil;
import com.clinica.odontologica.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthController authController;

    private AuthenticationRequest authenticationRequest;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("testuser@email.com");
        authenticationRequest.setPassword("password");

        // Asignar un rol al usuario
        userDetails = new User("testuser@email.com", "password", Collections.singletonList(() -> "ROLE_USER"));
    }

    @Test
    void testCreateAuthenticationToken_Success() {
        // Simular que la autenticación es exitosa
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        // Simular que el usuario ha sido cargado correctamente
        when(usuarioService.loadUserByUsername("testuser@email.com")).thenReturn(userDetails);

        // Simular la generación de un token JWT
        when(jwtUtil.generateToken("testuser@email.com", "ROLE_USER")).thenReturn("jwt_token");

        // Llamar al método a probar
        ResponseEntity<?> response = authController.createAuthenticationToken(authenticationRequest);

        // Verificar que la respuesta es correcta
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof AuthenticationResponse);
        assertEquals("jwt_token", ((AuthenticationResponse) response.getBody()).getJwt());

        // Verificar que los métodos se llamaron
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioService, times(1)).loadUserByUsername("testuser@email.com");
        verify(jwtUtil, times(1)).generateToken("testuser@email.com", "ROLE_USER");
    }

    @Test
    void testCreateAuthenticationToken_BadCredentials() {
        // Simular que la autenticación falla debido a credenciales incorrectas
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciales incorrectas"));

        // Verificar que se lanza una excepción 401 (UNAUTHORIZED)
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authController.createAuthenticationToken(authenticationRequest));

        assertEquals(401, exception.getStatusCode().value());
        assertEquals("Usuario o contraseña incorrectos", exception.getReason());
    }

    @Test
    void testCreateAuthenticationToken_ServerError() {
        // Simular un error interno del servidor durante la autenticación
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Error del servidor"));

        // Verificar que se lanza una excepción 500 (INTERNAL_SERVER_ERROR)
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authController.createAuthenticationToken(authenticationRequest));

        assertEquals(500, exception.getStatusCode().value());
        assertEquals("Error interno del servidor", exception.getReason());
    }

    @Test
    void testCreateAuthenticationToken_ForbiddenError() {
        // Simular que el usuario no tiene permisos
        when(usuarioService.loadUserByUsername(anyString())).thenThrow(new RuntimeException("Acceso denegado"));

        // Verificar que se lanza una excepción 403 (FORBIDDEN)
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authController.createAuthenticationToken(authenticationRequest));

        assertEquals(403, exception.getStatusCode().value());
        assertEquals("Acceso denegado", exception.getReason());
    }
}

