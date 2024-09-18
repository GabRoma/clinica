package com.clinica.odontologica.controller;
import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
@Validated
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private Mapper mapper;

    // Endpoint para listar odontólogos
    @GetMapping
    public ResponseEntity<List<OdontologoDTO>> listar() {
        List<OdontologoDTO> odontologos = odontologoService.listarOdontologos();
        return ResponseEntity.ok(odontologos);
    }

//     Endpoint para agregar un odontólogo
    @PostMapping("/agregar")
    public ResponseEntity<OdontologoDTO> agregar(@RequestBody OdontologoDTO odontologoDTO) {
        OdontologoDTO nuevoOdontologo = odontologoService.guardarOdontologo(odontologoDTO);
        return ResponseEntity.status(201).body(nuevoOdontologo);  // 201 Created
    }

    // Endpoint para obtener un odontólogo por su ID
    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoDTO> obtener(@PathVariable Long id) {
        OdontologoDTO odontologo = odontologoService.obtenerOdontologo(id);
        return ResponseEntity.ok(odontologo);  // 200 OK
    }

    // Endpoint para eliminar un odontólogo por su ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}