package com.clinica.odontologica.security;

import com.clinica.odontologica.entity.Usuario;
import com.clinica.odontologica.entity.UsuarioRol;
import com.clinica.odontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * La clase `InitialData` se ejecuta al iniciar la aplicación y se encarga de insertar datos iniciales en la base de datos.
 * Implementa la interfaz `ApplicationRunner` para ejecutar el método `run` después de que la aplicación se haya iniciado.
 */
@Component
public class InitialData implements ApplicationRunner {

    // Inyección de dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Método que se ejecuta al iniciar la aplicación.
     * Inserta un usuario administrador con una contraseña codificada en la base de datos.
     * param args Argumentos de la aplicación.
     * throws Exception Si ocurre un error durante la ejecución.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Codificar la contraseña "admin"
        String pass = bCryptPasswordEncoder.encode("admin");

        // Crear un nuevo usuario con rol de administrador
        Usuario usuario = new Usuario("Admin", "admin", pass, "admin@clinica.com", UsuarioRol.ROLE_ADMIN);

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuario);

        // Imprimir la contraseña codificada en la consola
        System.out.println(pass);
    }
}
