/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Examen;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ExamenRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ExamenDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahsan
 */
@Service
@RequiredArgsConstructor
public class ExamenService {
    
    private final ExamenRepository examenRepository;
    private final PreguntaRepository preguntaRepository;
    
    @Autowired
    private final ExamenMapper examenMapper;
    
    public List<ExamenDTO> getExamenes(String idProfesor) throws Exception {
        Iterable<Examen> examenes = examenRepository.findAllExamenesByIdProfesor(idProfesor);
        List<ExamenDTO> resp = new ArrayList<>();
        for(Examen examen : examenes){
            Integer cantidadpreguntas = preguntaRepository.findCantidadPreguntasByIdExamen(examen.getId());
            ExamenDTO examenDTO = examenMapper.fromExamen(examen);
            examenDTO.setProfesor_id(Long.valueOf(idProfesor));
            examenDTO.setCantidad_preguntas(cantidadpreguntas);
            resp.add(examenDTO);
        }
        return resp;
    }
    
}
