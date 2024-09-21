package com.clinica.odontologica.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * La clase `PasswordEncoder` es una configuración de Spring que define un bean para codificar contraseñas utilizando BCrypt.
 * El método `bCryptPasswordEncoder` crea y devuelve una instancia de `BCryptPasswordEncoder`,
 * que se puede inyectar en otras partes de la aplicación para codificar contraseñas de manera segura.
 */
@Configuration
public class PasswordEncoder {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}