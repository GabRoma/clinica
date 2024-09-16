package com.clinica.odontologica.controller;

import com.clinica.odontologica.entity.Usuario;
import com.clinica.odontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/agregar")
    public String agregarUsuario(@ModelAttribute Usuario usuario, Model model) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.guardarUsuario(usuario);
        // Redirigir al formulario de login tras el registro exitoso
        return "redirect:/login";
    }
}
