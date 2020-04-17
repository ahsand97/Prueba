/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Examen;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.Profesor;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ExamenRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ProfesorRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ExamenDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final ProfesorRepository profesorRepository;
    
    @Autowired
    private final ExamenMapper examenMapper;
    
    public List<ExamenDTO> getExamenes(String idProfesor) {
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
    
    public ExamenDTO addExamen(ExamenDTO examenDTO) throws Exception{
        if(examenDTO.getDescripcion() == null || examenDTO.getNota_maxima() == null || examenDTO.getProfesor_id() == null){
            throw new Exception("Faltan datos");
        }
        else{
            if((examenDTO.getDescripcion().isBlank() || examenDTO.getDescripcion().isEmpty()) || examenDTO.getNota_maxima().isNaN()){
                throw new Exception("Faltan datos");
            }
            else{
                Examen examenToDB = examenMapper.toExamen(examenDTO);
                Optional<Profesor> profesorToFK = profesorRepository.findById(examenDTO.getProfesor_id());
                examenToDB.setProfesor(profesorToFK.get());
                Examen examenFromDB = examenRepository.save(examenToDB);
                ExamenDTO resp = examenMapper.fromExamen(examenFromDB);
                resp.setProfesor_id(profesorToFK.get().getId());
                return resp; 
            }
        }
    }  
    
    public Boolean deleteExamen(String idExamen) throws Exception{
        if(idExamen == null || idExamen.isBlank() || idExamen.isEmpty()){
            throw new Exception("id Nula");
        }
        else{
            Optional<Examen> examenToRemove = examenRepository.findById(Long.valueOf(idExamen));
            if(examenToRemove.get() == null){
                throw new Exception("No existe el exámen");
            }
            else{
                examenRepository.delete(examenToRemove.get());
                return true;
            }
        }
    }
    
    public ExamenDTO getExamen(String idExamen) throws Exception{
        Optional<Examen> examen = examenRepository.findById(Long.valueOf(idExamen));
        if(examen == null){
            throw new Exception("Examen no encontrado");
        }
        else{
            ExamenDTO resp = examenMapper.fromExamen(examen.get());
            return resp;
        }
    }
}
