package com.clinica.odontologica.Tests;

import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.repository.OdontologoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OdontologoRepositoryTest {

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Test
    public void testGuardarYBuscarOdontologo() {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Ana");
        odontologo.setApellido("García");
        odontologo.setMatricula("123456");

        odontologoRepository.save(odontologo);

        Odontologo encontrado = odontologoRepository.findById(odontologo.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNombre());
    }
}