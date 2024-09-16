package com.clinica.odontologica.security;
import com.clinica.odontologica.entity.Usuario;
import com.clinica.odontologica.entity.UsuarioRol;
import com.clinica.odontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialData implements ApplicationRunner{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        String pass = bCryptPasswordEncoder.encode("admin");
        Usuario usuario = new Usuario("Admin", "admin", pass, "admin@clinica.com", UsuarioRol.ROLE_ADMIN);
        usuarioRepository.save(usuario);
        System.out.println(pass);
    }
}
