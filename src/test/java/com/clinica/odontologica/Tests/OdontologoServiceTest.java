package com.clinica.odontologica.Tests;

import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.OdontologoRepository;
import com.clinica.odontologica.service.OdontologoService;
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
public class OdontologoServiceTest {

    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private OdontologoService odontologoService;

    private Odontologo odontologo;
    private OdontologoDTO odontologoDTO;

    @BeforeEach
    void setUp() {
        odontologo = new Odontologo();
        odontologo.setId(1L);
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");

        odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(1L);
        odontologoDTO.setNombre("Juan");
        odontologoDTO.setApellido("Perez");
    }

    @Test
    @Order(1)
    void testListarOdontologos() {
        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(odontologo);

        when(odontologoRepository.findAll()).thenReturn(odontologos);
        when(mapper.odontologoToDto(any(Odontologo.class))).thenReturn(odontologoDTO);

        List<OdontologoDTO> result = odontologoService.listarOdontologos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(odontologoRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testGuardarOdontologo() {
        when(mapper.dtoToOdontologo(any(OdontologoDTO.class))).thenReturn(odontologo);
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);
        when(mapper.odontologoToDto(any(Odontologo.class))).thenReturn(odontologoDTO);

        OdontologoDTO result = odontologoService.guardarOdontologo(odontologoDTO);

        assertNotNull(result);
        assertEquals(odontologoDTO.getId(), result.getId());
        verify(odontologoRepository, times(1)).save(any(Odontologo.class));
    }

    @Test
    @Order(3)
    void testObtenerOdontologo() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.of(odontologo));
        when(mapper.odontologoToDto(any(Odontologo.class))).thenReturn(odontologoDTO);

        OdontologoDTO result = odontologoService.obtenerOdontologo(1L);

        assertNotNull(result);
        assertEquals(odontologoDTO.getId(), result.getId());
        verify(odontologoRepository, times(1)).findById(anyLong());
    }

    @Test
    @Order(4)
    void testObtenerOdontologoNotFound() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.obtenerOdontologo(1L));
        verify(odontologoRepository, times(1)).findById(anyLong());
    }

    @Test
    @Order(5)
    void testEliminarOdontologo() {
        when(odontologoRepository.existsById(anyLong())).thenReturn(true);

        odontologoService.eliminarOdontologo(1L);

        verify(odontologoRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @Order(6)
    void testEliminarOdontologoNotFound() {
        when(odontologoRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(1L));
        verify(odontologoRepository, times(1)).existsById(anyLong());
    }
}