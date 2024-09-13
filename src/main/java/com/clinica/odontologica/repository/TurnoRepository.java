package com.clinica.odontologica.repository;
import com.clinica.odontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{
}
