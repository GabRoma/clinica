package com.clinica.odontologica.repository;
import com.clinica.odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long>{

//    Optional<Odontologo> findById(Long id);
//    List<Odontologo> findAll(List<Odontologo> odontologos);

}
