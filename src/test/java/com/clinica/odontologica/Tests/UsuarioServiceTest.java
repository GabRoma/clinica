package com.clinica.odontologica.Tests;

import com.clinica.odontologica.dto.UsuarioDTO;
import com.clinica.odontologica.entity.Usuario;
import com.clinica.odontologica.entity.UsuarioRol;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.UsuarioRepository;
import com.clinica.odontologica.service.UsuarioService;
import com.clinica.odontologica.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        UsuarioRol role = UsuarioRol.ROLE_USER;

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Pedro");
        usuario.setApellido("Gonzalez");
        usuario.setEmail("test@example.com");
        usuario.setPassword("password");
        usuario.setUsuarioRol(role);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("Pedro");
        usuarioDTO.setApellido("Gonzalez");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setPassword("password");
        usuarioDTO.setUsuarioRol(role);
    }

    @Test
    @Order(1)
    void testGuardarUsuario() {
        when(mapper.dtoToUsuario(any(UsuarioDTO.class))).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.usuarioToDto(any(Usuario.class))).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.guardarUsuario(usuarioDTO);

        assertNotNull(result);
        assertEquals(usuarioDTO.getId(), result.getId());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @Order(2)
    void testEliminarUsuario() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(true);

        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @Order(3)
    void testEliminarUsuarioNotFound() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.eliminarUsuario(1L));
        verify(usuarioRepository, times(1)).existsById(anyLong());
    }

    @Test
    @Order(4)
    void testLoadUserByUsername() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.loadUserByUsername("test@example.com"));
        verify(usuarioRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @Order(5)
    void testLoadUserByUsernameNotFound() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> usuarioService.loadUserByUsername("test@example.com"));
        verify(usuarioRepository, times(1)).findByEmail(anyString());
    }
}