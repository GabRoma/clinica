package com.clinica.odontologica.Tests;

import com.clinica.odontologica.entity.Usuario;
import com.clinica.odontologica.entity.UsuarioRol;
import com.clinica.odontologica.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Order(1)
    public void testGuardarYBuscarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("password");
        usuario.setUsuarioRol(UsuarioRol.ROLE_USER);

        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findById(usuario.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("juan@example.com", encontrado.getEmail());
    }

    @Test
    @Order(2)
    public void testBuscarUsuarioPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("password");
        usuario.setUsuarioRol(UsuarioRol.ROLE_USER);

        usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findByEmail("juan@example.com");
        assertTrue(encontrado.isPresent());
        assertEquals("juan@example.com", encontrado.get().getEmail());
    }
}