package com.clinica.odontologica;

import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.entity.Domicilio;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.PacienteRepository;
import com.clinica.odontologica.utils.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    public void testListarPacientes() {
        // Datos simulados
        Domicilio domicilio1 = new Domicilio("Avenida Pino", 222, "Ciudad Grande", "Tierra");
        Domicilio domicilio2 = new Domicilio("Camino Norte", 2424, "Pueblo Este", "Central");
        Paciente paciente1 = new Paciente(1L, "Juan", "Pérez", "12345678", LocalDate.of(2024,2,10), "juan@gmail.com", domicilio1);
        Paciente paciente2 = new Paciente(2L, "Ana", "Gómez", "87654321", null, "ana@gmail.com", domicilio2);
        List<Paciente> listaDePacientes = new ArrayList<>(Arrays.asList(paciente1, paciente2));

        when(pacienteRepository.findAll())
                .thenReturn(listaDePacientes);

        PacienteDTO pacienteDTO1 = new PacienteDTO();
        pacienteDTO1.setId(1L);
        pacienteDTO1.setNombre("Juan");
        pacienteDTO1.setApellido("Pérez");
        pacienteDTO1.setCedula("12345678");

        PacienteDTO pacienteDTO2 = new PacienteDTO();
        pacienteDTO2.setId(2L);
        pacienteDTO2.setNombre("Ana");
        pacienteDTO2.setApellido("Gómez");
        pacienteDTO2.setCedula("87654321");

        when(mapper.pacienteToDto(paciente1)).thenReturn(pacienteDTO1);
        when(mapper.pacienteToDto(paciente2)).thenReturn(pacienteDTO2);

        // Ejecutamos el servicio
        List<PacienteDTO> result = pacienteService.listarPacientes();

        // Verificamos los resultados
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("Ana", result.get(1).getNombre());

        // Verificamos que el método findAll fue llamado una vez
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerPaciente() {
        // Dato simulado
        Domicilio domicilio = new Domicilio("Calle Paramo", 101, "Pueblo Chico", "Ejemplia");
        Paciente paciente = new Paciente(1L, "Juan", "Pérez", "12345678", LocalDate.of(2024,9,10), "juan@gmail.com", domicilio);

        // Configuramos Mockito
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(1L);
        pacienteDTO.setNombre("Juan");
        pacienteDTO.setApellido("Pérez");
        pacienteDTO.setCedula("12345678");

        when(mapper.pacienteToDto(paciente)).thenReturn(pacienteDTO);

        // Ejecutamos el servicio
        PacienteDTO result = pacienteService.obtenerPaciente(1L);

        // Verificamos los resultados
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());

        // Verificamos que el método findById fue llamado una vez
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerPaciente_NotFound() {
        // Configuramos Mockito
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutamos el servicio y esperamos la excepción
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.obtenerPaciente(1L));

        // Verificamos que el método findById fue llamado una vez
        verify(pacienteRepository, times(1)).findById(1L);
    }
}

