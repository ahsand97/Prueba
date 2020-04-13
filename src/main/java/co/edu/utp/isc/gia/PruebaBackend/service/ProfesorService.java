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
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ProfesorDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahsan
 */
@Service
@RequiredArgsConstructor
public class ProfesorService {
    
    private final ProfesorRepository profesorRepository;
    private final ExamenRepository examenRepository;
    private final PreguntaRepository preguntaRepository;

    @Autowired
    private final ProfesorMapper profesorMapper;
    
    @Autowired
    private final ExamenMapper examenMapper;
    
    public ProfesorDTO validate(Map<String, String> profesor) throws Exception {
        Profesor profesorFromDB = profesorRepository.findByEmail(profesor.get("email"));
        if(profesorFromDB == null){
            throw new Exception("Profesor no encontrado en el sistema");
        }
        else{
            if(profesor.get("clave").equals(profesorFromDB.getClave())){
                ProfesorDTO resp = profesorMapper.fromProfesor(profesorFromDB);
                return resp;
            }
            else{
                throw new Exception("La contraseña no coincide");
            }
        }
    }
    
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