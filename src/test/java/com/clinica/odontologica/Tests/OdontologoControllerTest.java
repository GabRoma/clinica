package com.clinica.odontologica.Tests;

import com.clinica.odontologica.controller.OdontologoController;
import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.service.OdontologoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class OdontologoControllerTest {

    @Autowired
    private OdontologoController odontologoController;

    @MockBean
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void testListarOdontologos() {
        List<OdontologoDTO> odontologos = Arrays.asList(new OdontologoDTO());
        when(odontologoService.listarOdontologos()).thenReturn(odontologos);

        ResponseEntity<List<OdontologoDTO>> response = odontologoController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @Order(2)
    public void testAgregarOdontologo() {
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        when(odontologoService.guardarOdontologo(odontologoDTO)).thenReturn(odontologoDTO);

        ResponseEntity<OdontologoDTO> response = odontologoController.agregar(odontologoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    public void testEliminarOdontologo() {
        doNothing().when(odontologoService).eliminarOdontologo(1L);

        ResponseEntity<Void> response = odontologoController.eliminar(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}