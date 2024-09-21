package com.clinica.odontologica.repository;
import com.clinica.odontologica.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String correo); // Busca un usuario por su correo electrónico
}
