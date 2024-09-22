package com.clinica.odontologica.Tests;

import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.exception.ConflictException;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.TurnoRepository;
import com.clinica.odontologica.service.TurnoService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private TurnoService turnoService;

    private Turno turno;
    private TurnoDTO turnoDTO;
    private Paciente paciente;
    private Odontologo odontologo;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Carlos");
        paciente.setApellido("Gomez");

        odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");

        turno = new Turno();
        turno.setId(1L);
        turno.setFechaHora(LocalDateTime.now());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        turnoDTO = new TurnoDTO();
        turnoDTO.setId(1L);
        turnoDTO.setFechaHora(LocalDateTime.now());
        turnoDTO.setPaciente(mapper.pacienteToDto(paciente));
        turnoDTO.setOdontologo(mapper.odontologoToDto(odontologo));
    }

    @Test
    @Order(1)
    void testListarTurnos() {
        List<Turno> turnos = new ArrayList<>();
        turnos.add(turno);

        when(turnoRepository.findAll()).thenReturn(turnos);
        when(mapper.turnoToDTO(any(Turno.class))).thenReturn(turnoDTO);

        List<TurnoDTO> result = turnoService.listarTurnos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testGuardarTurno() {
        when(mapper.dtoToTurno(any(TurnoDTO.class))).thenReturn(turno);
        when(turnoRepository.save(any(Turno.class))).thenReturn(turno);
        when(mapper.turnoToDTO(any(Turno.class))).thenReturn(turnoDTO);

        TurnoDTO result = turnoService.guardarTurno(turnoDTO, paciente, odontologo);

        assertNotNull(result);
        assertEquals(turnoDTO.getId(), result.getId());
        verify(turnoRepository, times(1)).save(any(Turno.class));
    }

    @Test
    @Order(3)
    void testObtenerTurno() {
        when(turnoRepository.findById(anyLong())).thenReturn(Optional.of(turno));
        when(mapper.turnoToDTO(any(Turno.class))).thenReturn(turnoDTO);

        TurnoDTO result = turnoService.obtenerTurno(1L);

        assertNotNull(result);
        assertEquals(turnoDTO.getId(), result.getId());
        verify(turnoRepository, times(1)).findById(anyLong());
    }

    @Test
    @Order(4)
    void testObtenerTurnoNotFound() {
        when(turnoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> turnoService.obtenerTurno(1L));
        verify(turnoRepository, times(1)).findById(anyLong());
    }

    @Test
    @Order(5)
    void testEliminarTurno() {
        when(turnoRepository.existsById(anyLong())).thenReturn(true);

        turnoService.eliminarTurno(1L);

        verify(turnoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @Order(6)
    void testEliminarTurnoNotFound() {
        when(turnoRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurno(1L));
        verify(turnoRepository, times(1)).existsById(anyLong());
    }
}