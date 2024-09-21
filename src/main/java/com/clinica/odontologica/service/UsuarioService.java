package com.clinica.odontologica.service;

import com.clinica.odontologica.entity.Usuario;
import com.clinica.odontologica.dto.UsuarioDTO;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.utils.Mapper;
import com.clinica.odontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Collections;
import java.util.Optional;

/**
 * La clase `UsuarioService` proporciona servicios para gestionar usuarios.
 * Implementa la interfaz `UserDetailsService` para la autenticación de usuarios.
 */
@Service
public class UsuarioService implements UserDetailsService {

    // Logger para registrar información y errores
    private static final Logger logger = LogManager.getLogger(UsuarioService.class);

    // Inyección de dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Mapper mapper;

    // Método para cargar un usuario por su nombre de usuario (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Buscando usuario por email: " + username);

        // Buscar el usuario en el repositorio por su email
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);

        // Si el usuario es encontrado, mapearlo a un objeto `User` de Spring Security
        return usuarioBuscado.map(usuario -> {
            String rol = (usuario.getUsuarioRol()).toString();
            GrantedAuthority authority = new SimpleGrantedAuthority(rol);
            System.out.println(authority);
            return new User(
                    usuario.getEmail(),
                    usuario.getPassword(),
                    Collections.singletonList(authority)
            );
        }).orElseThrow(() -> new UsernameNotFoundException("No se ha logrado encontrar al usuario: " + username));
    }

    // Método para registrar un nuevo usuario
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {
        logger.info("Guardando usuario: " + usuarioDTO.toString());
        // Convertir el DTO a entidad y guardar en la base de datos
        Usuario usuario = mapper.dtoToUsuario(usuarioDTO);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        // Convertir la entidad guardada a DTO y devolver
        return mapper.usuarioToDto(usuarioGuardado);
    }

    // Método para eliminar un usuario por su ID
    public void eliminarUsuario(Long id) {
        logger.info("Eliminando usuario por ID: " + id);
        // Verificar si el usuario existe antes de eliminar
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
