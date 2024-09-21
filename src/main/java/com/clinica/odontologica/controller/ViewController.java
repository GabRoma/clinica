package com.clinica.odontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@Controller
public class ViewController {

    @Operation(summary = "Cargar la página de inicio", description = "Carga la página de inicio del sistema.")
    @GetMapping("/")
    public String index() {
        return "index"; // Carga el archivo index.html en templates/
    }

    @Operation(summary = "Cargar la página de inicio", description = "Carga la página de inicio del sistema.")
    @GetMapping("/home")
    public String home() {
        return "home"; // Carga home.html
    }

    @Operation(summary = "Cargar la página de login", description = "Carga la página de login del sistema.")
    @GetMapping("/login")
    public String login() {
        return "login"; // Carga login.html
    }

    @Operation(summary = "Cargar la página de odontólogos", description = "Carga la página de odontólogos del sistema.")
    @GetMapping("/view/odontologos")
    public String odontologos() {
        return "odontologos"; // Carga odontologos.html
    }

    @Operation(summary = "Cargar la página de pacientes", description = "Carga la página de pacientes del sistema.")
    @GetMapping("/view/pacientes")
    public String pacientes() {
        return "pacientes"; // Carga pacientes.html
    }

    @Operation(summary = "Cargar la página de turnos", description = "Carga la página de turnos del sistema.")
    @GetMapping("/view/turnos")
    public String turnos() {
        return "turnos"; // Carga turnos.html
    }

    @Operation(summary = "Cargar la página de registrar usuario", description = "Carga la página de registrar usuario del sistema.")
    @GetMapping("/registrar")
    public String registrarUsuario() {
        return "registrar"; // Carga registrar.html desde la carpeta templates
    }
}
