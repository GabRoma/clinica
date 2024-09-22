package com.clinica.odontologica.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La clase `JwtUtil` proporciona utilidades para la creación y validación de tokens JWT.
 */
@Component
public class JwtUtil {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(JwtUtil.class);

    // Clave secreta para firmar los tokens JWT
    private String SECRET_KEY = "clave_secreta";

    /**
     * Genera un token JWT para un usuario dado.
     * param username El nombre de usuario.
     * param role El rol del usuario.
     * return El token JWT generado.
     */
    public String generateToken(String username, String role) {
        logger.info("Generando token para el usuario: " + username + "con rol: " + role);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);  // Añadir el rol al token
        return createToken(claims, username);
    }

    /**
     * Crea un token JWT con los claims y el sujeto dados.
     * param claims Los claims a incluir en el token.
     * param subject El sujeto del token (normalmente el nombre de usuario).
     * return El token JWT creado.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        logger.info("Creando token para el usuario: " + subject);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Expira en 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // Firma con la clave secreta
                .compact();
    }

    /**
     * Extrae el nombre de usuario de un token JWT.
     * param token El token JWT.
     * return El nombre de usuario extraído.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae el rol de un token JWT.
     * param token El token JWT.
     * return El rol extraído.
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    /**
     * Extrae un claim específico de un token JWT.
     * param token El token JWT.
     * param claimsResolver La función para resolver el claim.
     * return El claim extraído.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims de un token JWT.
     * param token El token JWT.
     * return Los claims extraídos.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Verifica si un token JWT ha expirado.
     * param token El token JWT.
     * return true si el token ha expirado, false en caso contrario.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración de un token JWT.
     * param token El token JWT.
     * return La fecha de expiración extraída.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Valida un token JWT comparando el nombre de usuario y verificando su expiración.
     * param token El token JWT.
     * param username El nombre de usuario.
     * return true si el token es válido, false en caso contrario.
     */
    public Boolean validateToken(String token, String username) {
        logger.info("Validando token para el usuario: " + username);
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}