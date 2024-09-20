package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.UsuarioDTO;
import com.clinica.odontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        UsuarioDTO nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO);
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
}
