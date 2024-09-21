package com.clinica.odontologica.Tests;

import com.clinica.odontologica.entity.Domicilio;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.repository.OdontologoRepository;
import com.clinica.odontologica.repository.PacienteRepository;
import com.clinica.odontologica.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
public class TurnoRepositoryTest {

    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Test
    @Order(1)
    public void testGuardarYBuscarTurno() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Springland");

        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setCedula("12345678");
        paciente.setFechaIngreso(LocalDate.parse("2021-01-01"));
        paciente.setEmail("juan.perez@example.com");
        paciente.setDomicilio(domicilio);

        pacienteRepository.save(paciente);

        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Ana");
        odontologo.setApellido("García");
        odontologo.setMatricula("123456");

        odontologoRepository.save(odontologo);

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFechaHora(LocalDateTime.parse("2025-01-01T10:00:00"));

        turnoRepository.save(turno);

        Turno encontrado = turnoRepository.findById(turno.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals(odontologo.getNombre(), encontrado.getOdontologo().getNombre());
    }

    @Test
    @Order(2)
    public void testExisteTurnoPorOdontologoYFechaHora() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Springland");

        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setCedula("12345678");
        paciente.setFechaIngreso(LocalDate.parse("2021-01-01"));
        paciente.setEmail("juan.perez@example.com");
        paciente.setDomicilio(domicilio);

        pacienteRepository.save(paciente);

        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Ana");
        odontologo.setApellido("García");
        odontologo.setMatricula("123456");

        odontologoRepository.save(odontologo);

        LocalDateTime fechaHora = LocalDateTime.parse("2025-01-01T10:00:00");

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFechaHora(fechaHora);

        turnoRepository.save(turno);

        boolean exists = turnoRepository.existsByOdontologoAndFechaHora(odontologo, fechaHora);
        assertTrue(exists);
    }
}