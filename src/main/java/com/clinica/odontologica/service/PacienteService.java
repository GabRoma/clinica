package com.clinica.odontologica.service;

import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.PacienteRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private Mapper mapper;

    // Listar pacientes
    public List<PacienteDTO> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteDTO> listaDTO = new ArrayList<>();
        for(Paciente paciente : pacientes){
            listaDTO.add(mapper.pacienteToDto(paciente));
        }
        return listaDTO;
    }

    // Guardar un paciente
    public PacienteDTO guardarPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = mapper.dtoToPaciente(pacienteDTO);
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        return mapper.pacienteToDto(pacienteGuardado);
    }

    // Obtener un paciente por su ID
    public PacienteDTO obtenerPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        return mapper.pacienteToDto(paciente);
    }

    // Método que obtiene la entidad Paciente directamente por su ID
    public Paciente obtenerEntidadPaciente(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    // Eliminar un paciente por su ID
    public void eliminarPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        pacienteRepository.deleteById(id);
    }
}
