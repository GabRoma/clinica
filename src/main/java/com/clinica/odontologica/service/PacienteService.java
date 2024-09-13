package com.clinica.odontologica.service;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.dto.PacienteDTO;
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
    public List<PacienteDTO> listarPacientes() {
        List<Paciente> listaPacientes = pacienteRepository.findAll();
        List<PacienteDTO> listaDTO = new ArrayList<>();
        for (Paciente paciente : listaPacientes){
            listaDTO.add(mapper.pacienteToDto(paciente));
        }
        return listaDTO;
    }

    public PacienteDTO guardarPaciente(Paciente paciente) {
        Paciente nuevoPaciente = pacienteRepository.save(paciente);
        return mapper.pacienteToDto(nuevoPaciente);
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }

    public Paciente obtenerPaciente(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }
}