package com.clinica.odontologica.Tests;

import com.clinica.odontologica.controller.TurnoController;
import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.dto.PacienteDTO;
import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.entity.Domicilio;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.service.OdontologoService;
import com.clinica.odontologica.service.PacienteService;
import com.clinica.odontologica.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TurnoControllerTest {

    @Autowired
    private TurnoController turnoController;

    @MockBean
    private TurnoService turnoService;

    @MockBean
    private PacienteService pacienteService; // Mock del PacienteService

    @MockBean
    private OdontologoService odontologoService; // Mock del OdontologoService

    @Test
    @Order(1)
    public void testListarTurnos() {
        List<TurnoDTO> turnos = Arrays.asList(new TurnoDTO());
        when(turnoService.listarTurnos()).thenReturn(turnos);

        ResponseEntity<List<TurnoDTO>> response = turnoController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @Order(2)
    public void testAgregarTurno() {
        TurnoDTO turnoDTO = new TurnoDTO();

        // Datos del domicilio
        Domicilio domicilio = new Domicilio();
        domicilio.setId(1L);
        domicilio.setCalle("Calle");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Localidad");
        domicilio.setProvincia("Provincia");

        // Datos del paciente (DTO)
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(1L);
        pacienteDTO.setNombre("Pepe");
        pacienteDTO.setApellido("Pepitos");
        pacienteDTO.setCedula("12345678");
        pacienteDTO.setFechaIngreso(LocalDate.parse("2023-10-10"));
        pacienteDTO.setEmail("Email");
        pacienteDTO.setDomicilio(domicilio);
        turnoDTO.setPaciente(pacienteDTO);

        // Datos del odontólogo (DTO)
        OdontologoDTO odontologoDTO = new OdontologoDTO();
        odontologoDTO.setId(1L);
        odontologoDTO.setNombre("Maria");
        odontologoDTO.setApellido("Perez");
        odontologoDTO.setMatricula("123456");
        turnoDTO.setOdontologo(odontologoDTO);

        turnoDTO.setFechaHora(LocalDateTime.parse("2025-10-10T10:00:00"));

        // Ahora vamos a simular las entidades Paciente y Odontologo (no los DTOs)
        Paciente pacienteMock = new Paciente();
        pacienteMock.setId(1L);
        pacienteMock.setNombre("Pepe");
        pacienteMock.setApellido("Pepitos");
        pacienteMock.setCedula("12345678");
        pacienteMock.setFechaIngreso(LocalDate.parse("2023-10-10"));
        pacienteMock.setEmail("Email");
        pacienteMock.setDomicilio(domicilio);

        Odontologo odontologoMock = new Odontologo();
        odontologoMock.setId(1L);
        odontologoMock.setNombre("Maria");
        odontologoMock.setApellido("Perez");
        odontologoMock.setMatricula("123456");

        // Mock de los servicios de paciente y odontólogo (no TurnoService)
        when(pacienteService.obtenerEntidadPaciente(turnoDTO.getPaciente().getId())).thenReturn(pacienteMock); // Mock de la entidad Paciente
        when(odontologoService.obtenerEntidadOdontologo(turnoDTO.getOdontologo().getId())).thenReturn(odontologoMock); // Mock de la entidad Odontologo

        // Simulación de la creación del turno
        when(turnoService.guardarTurno(any(TurnoDTO.class), any(Paciente.class), any(Odontologo.class))).thenReturn(turnoDTO);

        // Probar la respuesta
        ResponseEntity<TurnoDTO> response = turnoController.agregar(turnoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    public void testObtenerTurno() {
        TurnoDTO turnoDTO = new TurnoDTO();
        when(turnoService.obtenerTurno(1L)).thenReturn(turnoDTO);

        ResponseEntity<TurnoDTO> response = turnoController.obtener(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    public void testEliminarTurno() {
        doNothing().when(turnoService).eliminarTurno(1L);

        ResponseEntity<Void> response = turnoController.eliminar(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}