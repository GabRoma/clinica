package com.clinica.odontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index"; // Carga el archivo index.html en templates/
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Carga home.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Carga login.html
    }

    @GetMapping("/view/odontologos")
    public String odontologos() {
        return "odontologos"; // Carga odontologos.html
    }

    @GetMapping("/view/pacientes")
    public String pacientes() {
        return "pacientes"; // Carga pacientes.html
    }

    @GetMapping("/view/turnos")
    public String turnos() {
        return "turnos"; // Carga turnos.html
    }

    @GetMapping("/registrar")
    public String registrarUsuario() {
        return "registrar"; // Carga registrar.html desde la carpeta templates
    }
}
