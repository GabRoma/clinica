package com.clinica.odontologica.repository;
import com.clinica.odontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    Optional<Paciente> findByEmail(String correo);
}
