package com.clinica.odontologica.controller;
import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.service.TurnoService;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/turnos")
@Validated
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private Mapper mapper;

    // Endpoint para listar turnos
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listar() {
        List<TurnoDTO> turnos = turnoService.listarTurnos();
        return ResponseEntity.ok(turnos);
    }

    // Endpoint para agregar un turno
    @PostMapping("/agregar")
    public ResponseEntity<TurnoDTO> agregar(@RequestBody TurnoDTO turnoDTO) {
        Paciente paciente = pacienteService.obtenerEntidadPaciente(turnoDTO.getPaciente().getId());
        Odontologo odontologo = odontologoService.obtenerEntidadOdontologo(turnoDTO.getOdontologo().getId());
        if (paciente != null && odontologo != null) {
            TurnoDTO nuevoTurno = turnoService.guardarTurno(turnoDTO, paciente, odontologo);
            return ResponseEntity.status(201).body(nuevoTurno);  // 201 Created
        } else {
            return ResponseEntity.badRequest().build();  // 400 Bad Request
        }
    }

    // Endpoint para obtener un turno por su ID
    @GetMapping("/eliminar/{id}")
    public ResponseEntity<TurnoDTO> obtener(@PathVariable Long id) {
        TurnoDTO turno = turnoService.obtenerTurno(id);
        return ResponseEntity.ok(turno);  // 200 OK
    }

    // Endpoint para eliminar un turno por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
