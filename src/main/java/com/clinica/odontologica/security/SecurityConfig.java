package com.clinica.odontologica.security;

import com.clinica.odontologica.service.UsuarioService;
import com.clinica.odontologica.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

/**
 * La clase `SecurityConfig` configura la seguridad de la aplicación utilizando Spring Security.
 * Utiliza anotaciones de configuración para definir los beans y las reglas de seguridad.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Inyección de dependencias
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Configura la cadena de filtros de seguridad.
     * Define las reglas de autorización y la política de manejo de sesiones.
     * param http El objeto HttpSecurity utilizado para configurar la seguridad.
     * return El objeto SecurityFilterChain configurado.
     * throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)  // Deshabilita la protección CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index", "/login", "/registrar", "/usuarios/registrar", "/css/**", "/js/**", "/images/**").permitAll()  // Rutas públicas
                        .requestMatchers("/home", "/view/turnos", "/turnos").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")  // Rutas accesibles para USER y ADMIN
                        .requestMatchers("/view/odontologos", "/odontologos", "/pacientes", "/view/pacientes").hasAuthority("ROLE_ADMIN")  // Rutas exclusivas de ADMIN
                        .anyRequest().authenticated()  // Cualquier otra solicitud requiere autenticación
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Configura la política de manejo de sesiones como sin estado
                );

        // Agrega el filtro JWT antes del filtro de autenticación de usuario y contraseña
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura el proveedor de autenticación DAO.
     * Utiliza el servicio de usuario y el codificador de contraseñas para autenticar a los usuarios.
     * return El objeto DaoAuthenticationProvider configurado.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);  // Establece el servicio de usuario
        provider.setPasswordEncoder(bCryptPasswordEncoder);  // Establece el codificador de contraseñas
        return provider;
    }

    /**
     * Configura el administrador de autenticación.
     * Utiliza la configuración de autenticación para obtener el administrador de autenticación.
     * param authenticationConfiguration La configuración de autenticación.
     * return El objeto AuthenticationManager configurado.
     * throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}