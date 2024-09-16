package com.clinica.odontologica;

import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.service.TurnoService;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.TurnoRepository;
import com.clinica.odontologica.utils.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurnoServiceTest {

    @Mock
    private TurnoRepository turnoRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private TurnoService turnoService;

    @Test
    public void testListarTurnos() {
        // Datos simulados
        Turno turno1 = new Turno(1L, null, null, null);
        Turno turno2 = new Turno(2L, null, null, null);
        List<Turno> listaDeTurnos = new ArrayList<>(Arrays.asList(turno1, turno2));

        when(turnoRepository.findAll()).thenReturn(listaDeTurnos);

        TurnoDTO turnoDTO1 = new TurnoDTO();
        turnoDTO1.setId(1L);

        TurnoDTO turnoDTO2 = new TurnoDTO();
        turnoDTO2.setId(2L);

        when(mapper.turnoToDTO(turno1)).thenReturn(turnoDTO1);
        when(mapper.turnoToDTO(turno2)).thenReturn(turnoDTO2);

        // Ejecutamos el servicio
        List<TurnoDTO> result = turnoService.listarTurnos();

        // Verificamos los resultados
        assertEquals(2, result.size());

        // Verificamos que el método findAll fue llamado una vez
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerTurno() {
        // Dato simulado
        Turno turno = new Turno(1L, null, null, null);

        // Configuramos Mockito
        when(turnoRepository.findById(1L)).thenReturn(Optional.of(turno));

        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(1L);

        when(mapper.turnoToDTO(turno)).thenReturn(turnoDTO);

        // Ejecutamos el servicio
        TurnoDTO result = turnoService.obtenerTurno(1L);

        // Verificamos los resultados
        assertNotNull(result);

        // Verificamos que el método findById fue llamado una vez
        verify(turnoRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerTurno_NotFound() {
        // Configuramos Mockito
        when(turnoRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutamos el servicio y esperamos la excepción
        assertThrows(ResourceNotFoundException.class, () -> turnoService.obtenerTurno(1L));

        // Verificamos que el método findById fue llamado una vez
        verify(turnoRepository, times(1)).findById(1L);
    }
}
