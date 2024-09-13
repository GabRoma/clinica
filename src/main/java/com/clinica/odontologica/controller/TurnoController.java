package com.clinica.odontologica.controller;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.service.TurnoService;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping
    public List<TurnoDTO> listar() {
        return turnoService.listarTurnos();
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> agregar(@RequestBody Turno turno) {
        Optional<Paciente> paciente = Optional.ofNullable(pacienteService.obtenerPaciente(turno.getPaciente().getId()));
        Optional<Odontologo> odontologo = Optional.ofNullable(odontologoService.obtenerOdontologo(turno.getOdontologo().getId()));
        if (paciente.isPresent()&&odontologo.isPresent()){
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Turno>> obtener(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = Optional.ofNullable(turnoService.obtenerTurno(id));
        if (turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado);
        }else{
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }
}