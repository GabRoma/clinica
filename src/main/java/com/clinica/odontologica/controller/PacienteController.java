package com.clinica.odontologica.controller;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<PacienteDTO> listar() {
        return pacienteService.listarPacientes();
    }

    @PostMapping
    public ResponseEntity<PacienteDTO> agregar(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Paciente>> obtener(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = Optional.ofNullable(pacienteService.obtenerPaciente(id));
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }else{
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}