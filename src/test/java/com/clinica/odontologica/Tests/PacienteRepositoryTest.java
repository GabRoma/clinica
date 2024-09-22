package com.clinica.odontologica.Tests;

import com.clinica.odontologica.entity.Domicilio;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    public void testGuardarYBuscarPaciente() {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Falsa");
        domicilio.setNumero(123);
        domicilio.setLocalidad("Springfield");
        domicilio.setProvincia("Springland");

        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Pérez");
        paciente.setCedula("12345678");
        paciente.setFechaIngreso(LocalDate.now());
        paciente.setEmail("juan.perez@example.com");
        paciente.setDomicilio(domicilio);

        pacienteRepository.save(paciente);

        Paciente encontrado = pacienteRepository.findById(paciente.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Juan", encontrado.getNombre());
    }
}