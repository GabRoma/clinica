package com.clinica.odontologica.Tests;

import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.PacienteRepository;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;
    private PacienteDTO pacienteDTO;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Carlos");
        paciente.setApellido("Gomez");

        pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(1L);
        pacienteDTO.setNombre("Carlos");
        pacienteDTO.setApellido("Gomez");
    }

    @Test
    @Order(1)
    void testListarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(paciente);

        when(pacienteRepository.findAll()).thenReturn(pacientes);
        when(mapper.pacienteToDto(any(Paciente.class))).thenReturn(pacienteDTO);

        List<PacienteDTO> result = pacienteService.listarPacientes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testGuardarPaciente() {
        when(mapper.dtoToPaciente(any(PacienteDTO.class))).thenReturn(paciente);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        when(mapper.pacienteToDto(any(Paciente.class))).thenReturn(pacienteDTO);

        PacienteDTO result = pacienteService.guardarPaciente(pacienteDTO);

        assertNotNull(result);
        assertEquals(pacienteDTO.getId(), result.getId());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @Order(3)
    void testObtenerPaciente() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
        when(mapper.pacienteToDto(any(Paciente.class))).thenReturn(pacienteDTO);

        PacienteDTO result = pacienteService.obtenerPaciente(1L);

        assertNotNull(result);
        assertEquals(pacienteDTO.getId(), result.getId());
        verify(pacienteRepository, times(1)).findById(anyLong());
    }

    @Test
    @Order(4)
    void testObtenerPacienteNotFound() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.obtenerPaciente(1L));
        verify(pacienteRepository, times(1)).findById(anyLong());
    }

    @Test
    @Order(5)
    void testEliminarPaciente() {
        when(pacienteRepository.existsById(anyLong())).thenReturn(true);

        pacienteService.eliminarPaciente(1L);

        verify(pacienteRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @Order(6)
    void testEliminarPacienteNotFound() {
        when(pacienteRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(1L));
        verify(pacienteRepository, times(1)).existsById(anyLong());
    }
}