package com.clinica.odontologica.Tests;

import com.clinica.odontologica.controller.PacienteController;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.service.PacienteService;
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
public class PacienteControllerTest {

    @Autowired
    private PacienteController pacienteController;

    @MockBean
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void testListarPacientes() {
        List<PacienteDTO> pacientes = Arrays.asList(new PacienteDTO());
        when(pacienteService.listarPacientes()).thenReturn(pacientes);

        ResponseEntity<List<PacienteDTO>> response = pacienteController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @Order(2)
    public void testAgregarPaciente() {
        PacienteDTO pacienteDTO = new PacienteDTO();
        when(pacienteService.guardarPaciente(pacienteDTO)).thenReturn(pacienteDTO);

        ResponseEntity<PacienteDTO> response = pacienteController.agregar(pacienteDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    public void testEliminarPaciente() {
        doNothing().when(pacienteService).eliminarPaciente(1L);

        ResponseEntity<Void> response = pacienteController.eliminar(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}