package com.clinica.odontologica.repository;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{
    boolean existsByOdontologoAndFechaHora(Odontologo odontologo, LocalDateTime fechaHora);
}
