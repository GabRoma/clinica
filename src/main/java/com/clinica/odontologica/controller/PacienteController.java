package com.clinica.odontologica.controller;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@Validated
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private Mapper mapper;

    // Endpoint para listar pacientes con paginación
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> listar(){
        List<PacienteDTO> pacientes = pacienteService.listarPacientes();
        return ResponseEntity.ok(pacientes);
    }

    // Endpoint para agregar un paciente, validando el DTO
    @PostMapping("/agregar")
    public ResponseEntity<PacienteDTO> agregar(@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO nuevoPaciente = pacienteService.guardarPaciente(pacienteDTO);
        return ResponseEntity.status(201).body(nuevoPaciente);  // 201 Created
    }

    // Endpoint para obtener un paciente por su ID, con manejo de excepciones
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PacienteDTO> obtener(@PathVariable Long id) {
        PacienteDTO paciente = pacienteService.obtenerPaciente(id);
        return ResponseEntity.ok(paciente);  // 200 OK
    }

    // Endpoint para eliminar un paciente por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
