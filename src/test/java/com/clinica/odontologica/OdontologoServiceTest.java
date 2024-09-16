package com.clinica.odontologica;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.OdontologoRepository;
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
public class OdontologoServiceTest {

    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private OdontologoService odontologoService;

    @Test
    public void testListarOdontologos() {
        // Datos simulados
        Odontologo odontologo1 = new Odontologo(1L, "Juan", "Pérez", "12345");
        Odontologo odontologo2 = new Odontologo(2L, "Ana", "Gómez", "54321");
        List<Odontologo> listaDeOdontologos = new ArrayList<>(Arrays.asList(odontologo1, odontologo2));

        when(odontologoRepository.findAll()).thenReturn(listaDeOdontologos);

        OdontologoDTO odontologoDTO1 = new OdontologoDTO();
        odontologoDTO1.setId(1L);
        odontologoDTO1.setNombre("Juan");
        odontologoDTO1.setApellido("Pérez");
        odontologoDTO1.setMatricula("12345");

        OdontologoDTO odontologoDTO2 = new OdontologoDTO();
        odontologoDTO2.setId(2L);
        odontologoDTO2.setNombre("Ana");
        odontologoDTO2.setApellido("Gómez");
        odontologoDTO2.setMatricula("54321");

        when(mapper.odontologoToDto(odontologo1)).thenReturn(odontologoDTO1);
        when(mapper.odontologoToDto(odontologo2)).thenReturn(odontologoDTO2);

        // Ejecutamos el servicio
        List<OdontologoDTO> result = odontologoService.listarOdontologos();

        // Verificamos los resultados
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getNombre());
        assertEquals("Ana", result.get(1).getNombre());

        // Verificamos que el método findAll fue llamado una vez
        verify(odontologoRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerOdontologo() {
        // Dato simulado
        Odontologo odontologo = new Odontologo(1L, "Juan", "Pérez", "12345");

        // Configuramos Mockito
        when(odontologoRepository.findById(1L)).thenReturn(Optional.of(odontologo));

        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(1L);
        odontologoDTO.setNombre("Juan");
        odontologoDTO.setApellido("Pérez");
        odontologoDTO.setMatricula("12345");

        when(mapper.odontologoToDto(odontologo)).thenReturn(odontologoDTO);

        // Ejecutamos el servicio
        OdontologoDTO result = odontologoService.obtenerOdontologo(1L);

        // Verificamos los resultados
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());

        // Verificamos que el método findById fue llamado una vez
        verify(odontologoRepository, times(1)).findById(1L);
    }

    @Test
    public void testObtenerOdontologo_NotFound() {
        // Configuramos Mockito
        when(odontologoRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutamos el servicio y esperamos la excepción
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.obtenerOdontologo(1L));

        // Verificamos que el método findById fue llamado una vez
        verify(odontologoRepository, times(1)).findById(1L);
    }
}
