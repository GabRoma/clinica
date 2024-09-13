package com.clinica.odontologica.service;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.dto.OdontologoDTO;
import com.clinica.odontologica.repository.OdontologoRepository;
import com.clinica.odontologica.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private Mapper mapper;
    public List<OdontologoDTO> listarOdontologos() {
        List<Odontologo> listaOdontologos = odontologoRepository.findAll();
        List<OdontologoDTO> listaDTO = new ArrayList<>();
        for(Odontologo odontologo : listaOdontologos){
            listaDTO.add(mapper.odontologoToDto(odontologo));
        }
        return listaDTO;
    }
    public OdontologoDTO guardarOdontologo(Odontologo odontologo) {
        Odontologo nuevoOdontologo = odontologoRepository.save(odontologo);
        return mapper.odontologoToDto(nuevoOdontologo);
    }

    public void eliminarOdontologo(Long id) {
        odontologoRepository.deleteById(id);
    }

    public Odontologo obtenerOdontologo(Long id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado"));
    }
}
