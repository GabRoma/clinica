package com.clinica.odontologica.service;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.OdontologoRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private Mapper mapper;

    // Listar odontólogos
    public List<OdontologoDTO> listarOdontologos() {
        List<Odontologo> listaOdontologos = odontologoRepository.findAll();
        List<OdontologoDTO> listaDTO = new ArrayList<>();
        for(Odontologo odontologo : listaOdontologos){
            listaDTO.add(mapper.odontologoToDto(odontologo));
        }
        return listaDTO;
    }

    // Guardar un odontólogo
    public OdontologoDTO guardarOdontologo(OdontologoDTO odontologoDTO) {
        Odontologo odontologo = mapper.dtoToOdontologo(odontologoDTO);
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        return mapper.odontologoToDto(odontologoGuardado);
    }

    // Obtener un odontólogo por su ID
    public OdontologoDTO obtenerOdontologo(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado"));
        return mapper.odontologoToDto(odontologo);
    }

    // Método que obtiene la entidad Odontologo directamente por su ID
    public Odontologo obtenerEntidadOdontologo(Long id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado"));
    }

    // Eliminar un odontólogo por su ID
    public void eliminarOdontologo(Long id) {
        if (!odontologoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Odontólogo no encontrado");
        }
        odontologoRepository.deleteById(id);
    }
}
