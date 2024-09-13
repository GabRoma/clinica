package com.clinica.odontologica.service;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.dto.TurnoDTO;
import com.clinica.odontologica.repository.TurnoRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    @Autowired
    private Mapper mapper;

    public List<TurnoDTO> listarTurnos(){
        List<Turno> listaTurnos = turnoRepository.findAll();
        List<TurnoDTO> listaDTO = new ArrayList<>();
        for(Turno turno : listaTurnos){
            listaDTO.add(mapper.turnoToDTO(turno));
        }
        return listaDTO;
    }

    public TurnoDTO guardarTurno(Turno turno){
        Turno nuevoTurno = turnoRepository.save(turno);
        return mapper.turnoToDTO(nuevoTurno);
    }

    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }

    public Turno obtenerTurno(Long id){
        return turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
    }
}
