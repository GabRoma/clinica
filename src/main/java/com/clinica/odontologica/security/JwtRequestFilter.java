package com.clinica.odontologica.security;

import com.clinica.odontologica.service.UsuarioService;
import com.clinica.odontologica.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * La clase `JwtRequestFilter` extiende `OncePerRequestFilter` para asegurar que el filtro se ejecute una vez por solicitud.
 * Se encarga de filtrar las solicitudes HTTP para validar el token JWT y autenticar al usuario.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(JwtRequestFilter.class);

    // Inyección de dependencias
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Método que filtra cada solicitud HTTP.
     * Extrae el token JWT del encabezado de autorización, lo valida y autentica al usuario.
     * param request La solicitud HTTP.
     * param response La respuesta HTTP.
     * param chain El filtro de la cadena.
     * throws ServletException Si ocurre un error en el filtro.
     * throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwt = null;

        // Obtener el encabezado de autorización de la solicitud
        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Encabezado de autorización: " + authorizationHeader);

        String username = null;

        // Verificar si el encabezado de autorización contiene un token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extraer el token JWT del encabezado
            jwt = authorizationHeader.substring(7);
            System.out.println("JWT recibido: " + jwt);
            try {
                // Extraer el nombre de usuario del token JWT
                username = jwtUtil.extractUsername(jwt);
                logger.info("Nombre de usuario extraído del JWT: " + username);
            } catch (Exception e) {
                logger.error("Error extracting username from JWT", e);
            }
        }

        // Verificar si el nombre de usuario no es nulo y no hay una autenticación previa en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargar los detalles del usuario utilizando el nombre de usuario
            UserDetails userDetails = this.usuarioService.loadUserByUsername(username);
            System.out.println("Detalles del usuario cargados: " + userDetails);

            // Validar el token JWT
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Crear un objeto de autenticación y establecer los detalles de la solicitud
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.info("Autenticación establecida para el usuario: " + username);
            } else {
                logger.warn("Token JWT no válido para el usuario: " + username);
            }
        } else {
            logger.warn("Nombre de usuario nulo o autenticación ya establecida");
        }

        // Continuar con el siguiente filtro en la cadena
        chain.doFilter(request, response);
    }

//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
}