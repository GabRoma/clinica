package com.clinica.odontologica.controller;
import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping
    public List<OdontologoDTO> listar() {
        return odontologoService.listarOdontologos();
    }

    @PostMapping
    public ResponseEntity<OdontologoDTO> agregar(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Odontologo>> obtener(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = Optional.ofNullable(odontologoService.obtenerOdontologo(id));
        if (odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado);
        }else{
            throw new ResourceNotFoundException("Odontólogo no encontrado");
        }
    }
}