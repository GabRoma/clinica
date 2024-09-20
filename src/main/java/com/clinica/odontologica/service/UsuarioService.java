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

import java.util.Collections;
import java.util.Optional;
@Service
public class UsuarioService implements UserDetailsService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Mapper mapper;

    //Obtener un usuario con su email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioBuscado = usuarioRepository.findByEmail(username);

        return usuarioBuscado.map(usuario -> {

            String rol = (usuario.getUsuarioRol()).toString();

            GrantedAuthority authority = new SimpleGrantedAuthority(rol);
            System.out.println("La authority es: " + authority);
            return new User(
                    usuario.getEmail(),
                    usuario.getPassword(),
                    Collections.singletonList(authority)
            );
        }).orElseThrow(() -> new UsernameNotFoundException("No se ha logrado encontrar al usuario: " + username));
    }

    //Registrar un nuevo usuario
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = mapper.dtoToUsuario(usuarioDTO);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapper.usuarioToDto(usuarioGuardado);
    }

    //Eliminar un usuario por su id
    public void eliminarUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}
