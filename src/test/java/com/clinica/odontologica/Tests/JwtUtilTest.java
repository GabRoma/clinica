package com.clinica.odontologica.Tests;

import com.clinica.odontologica.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtUtilTest es una clase que realiza pruebas unitarias para las funcionalidades de la clase JwtUtil,
 * incluyendo la generación, validación y extracción de claims de los tokens JWT.
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class JwtUtilTest {

    // Inyección de la clase JwtUtil para pruebas
    @Autowired
    private JwtUtil jwtUtil;

    // Variables para las pruebas
    private String token;
    private String username = "testuser@email.com";
    private String role = "ROLE_USER";

    /**
     * Método para configurar el token JWT antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        // Generar un token de prueba
        token = jwtUtil.generateToken(username, role);
    }

    /**
     * Prueba para verificar que el token generado no sea nulo y que tenga el formato esperado.
     */
    @Test
    @Order(1)
    public void testGenerateToken() {
        assertNotNull(token, "El token no debe ser nulo");
        assertTrue(token.startsWith("eyJ"), "El token debe comenzar con 'eyJ', lo que indica un JWT válido");
    }

    /**
     * Prueba para extraer el nombre de usuario del token y compararlo con el valor original.
     */
    @Test
    @Order(2)
    public void testExtractUsername() {
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername, "El nombre de usuario extraído debe coincidir con el original");
    }

    /**
     * Prueba para extraer el rol del usuario desde el token y verificar su validez.
     */
    @Test
    @Order(3)
    public void testExtractRole() {
        Claims claims = jwtUtil.extractAllClaims(token);
        String extractedRole = claims.get("role", String.class);
        assertEquals(role, extractedRole, "El rol extraído del token debe coincidir con el rol original");
    }

    /**
     * Prueba para verificar que la fecha de expiración del token sea válida y esté en el futuro.
     */
    @Test
    @Order(4)
    public void testExtractExpiration() {
        Date expirationDate = jwtUtil.extractExpiration(token);
        assertNotNull(expirationDate, "La fecha de expiración no debe ser nula");
        assertTrue(expirationDate.after(new Date()), "La fecha de expiración debe estar en el futuro");
    }

    /**
     * Prueba para validar que el token generado sea válido para el usuario y no esté expirado.
     */
    @Test
    @Order(5)
    public void testValidateToken() {
        boolean isValid = jwtUtil.validateToken(token, username);
        assertTrue(isValid, "El token debe ser válido para el usuario");
    }

    /**
     * Prueba para verificar si el token ha expirado (simulación).
     */
    @Test
    @Order(6)
    public void testTokenExpired() {
        // Simular que el token recién generado no ha expirado
        boolean isTokenExpired = jwtUtil.isTokenExpired(token);
        assertFalse(isTokenExpired, "El token recién generado no debe estar expirado");
    }

    @Test
    @Order(7)
    public void testExtractAllClaims() {
        Claims claims = jwtUtil.extractAllClaims(token);
        assertNotNull(claims, "Los claims no deben ser nulos");
        assertEquals(username, claims.getSubject(), "El nombre de usuario extraído debe coincidir con el original");
        assertEquals(role, claims.get("role", String.class), "El rol extraído del token debe coincidir con el rol original");
    }
}
