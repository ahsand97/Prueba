/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Estudiante;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.Examen;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.Opcion;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaMultiplesRespuestas;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaRespuestaAbierta;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaUnicaRespuesta;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.Respuesta;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.RespuestaAbierta;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.EstudianteRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ExamenRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaMultiplesRespuestasRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRespuestaAbiertaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaUnicaRespuestaRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.EstudianteDTO;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.RespuestaDTO;
import java.util.Iterator;
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
public class RespuestaService {
    
    private final EstudianteRepository estudianteRepository;
    private final ExamenRepository examenRepository;
    
    private final PreguntaRepository preguntaRepository;
    private final PreguntaRespuestaAbiertaRepository preguntaRARepository;
    private final PreguntaUnicaRespuestaRepository preguntaURRepository;
    private final PreguntaMultiplesRespuestasRepository preguntaMRRepository;
    
    @Autowired
    private final EstudianteMapper estudianteMapper;
    
    public EstudianteDTO addEstudiante(EstudianteDTO estudiante) throws Exception{
        if(estudiante == null || (estudiante.getEmail().isBlank() || estudiante.getEmail().isEmpty() || estudiante.getEmail() == null) || estudiante.getExamen_id() == null || (estudiante.getNombre() == null || estudiante.getNombre().isBlank() || estudiante.getNombre().isEmpty())){
            throw new Exception("Faltan datos");
        }
        else{
            Optional<Examen> toFK = examenRepository.findById(estudiante.getExamen_id());
            Estudiante toDB = estudianteMapper.toEstudiante(estudiante);
            toDB.setExamen(toFK.get());
            Estudiante respFromDB = estudianteRepository.save(toDB);
            EstudianteDTO resp = estudianteMapper.fromEstudiante(respFromDB);
            resp.setExamen_id(respFromDB.getExamen().getId());
            return resp;
        }
    }
    
    public RespuestaDTO addRespuesta(RespuestaDTO respuesta) throws Exception{
        if(respuesta == null || respuesta.getIdEstudiante() == null || respuesta.getNumeroPregunta() == null || (respuesta.getTipo() == null || respuesta.getTipo().isBlank() || respuesta.getTipo().isEmpty()) || (respuesta.getRespuesta() == null || respuesta.getRespuesta().isBlank() || respuesta.getRespuesta().isEmpty()) || respuesta.getValoracion() == null){
            throw new Exception("Faltan datos");
        }
        else{
            Optional<Estudiante> estudianteRelacionado = estudianteRepository.findById(respuesta.getIdEstudiante());
            Double notaEstudiante = estudianteRelacionado.get().getNota();
            Examen examenRelacionado = estudianteRelacionado.get().getExamen();
            Long idPreguntaRelacionada = preguntaRepository.findIdPregunta(String.valueOf(examenRelacionado.getId()), String.valueOf(respuesta.getNumeroPregunta()));
            if("Pregunta con respuesta abierta".equals(respuesta.getTipo())){
                Optional<PreguntaRespuestaAbierta> PRA = preguntaRARepository.findById(idPreguntaRelacionada);
                
                RespuestaAbierta respToDB = new RespuestaAbierta();
                respToDB.setPregunta(PRA.get());
                respToDB.setRespuesta(respuesta.getRespuesta());
                
                estudianteRelacionado.get().addRespuesta(respToDB);
                estudianteRepository.save(estudianteRelacionado.get());
                return respuesta;
            }
            else if("Pregunta con múltiples opciones y única respuesta".equals(respuesta.getTipo())){
                Optional<PreguntaUnicaRespuesta> PUR = preguntaURRepository.findById(idPreguntaRelacionada);
                
                Respuesta respToDB = new Respuesta();
                respToDB.setPregunta(PUR.get());
                
                Iterator<Opcion> crunchifyIterator = PUR.get().getOpciones().iterator();
                Opcion opcionToRespuesta = new Opcion();
                while(crunchifyIterator.hasNext()){
                    Opcion opcion = crunchifyIterator.next();
                    if(opcion.getLetra().equals(respuesta.getRespuesta())){
                        opcionToRespuesta = opcion;
                    } 
                }
                respToDB.addOpcion(opcionToRespuesta);
                
                Opcion opcionRespuesta = PUR.get().getOpcionRespuesta();
                if(opcionRespuesta.getLetra().equals(respuesta.getRespuesta())){
                    respuesta.setIsCorrect(true);
                    notaEstudiante = notaEstudiante + PUR.get().getValoracion();
                    estudianteRelacionado.get().setNota(notaEstudiante);
                }
                else{
                    respuesta.setIsCorrect(false);
                }
                
                estudianteRelacionado.get().addRespuesta(respToDB);
                estudianteRepository.save(estudianteRelacionado.get());
                return respuesta;
            }
            else if("Pregunta con múltiples opciones y múltiples respuestas".equals(respuesta.getTipo())){
                Optional<PreguntaMultiplesRespuestas> PMR = preguntaMRRepository.findById(idPreguntaRelacionada);
                
                Respuesta respToDB = new Respuesta();
                respToDB.setPregunta(PMR.get());
                
                Iterator<Opcion> crunchifyIterator = PMR.get().getOpciones().iterator();
                Opcion opcionToRespuesta = new Opcion();
                while(crunchifyIterator.hasNext()){
                    Opcion opcion = crunchifyIterator.next();
                    if(opcion.getLetra().equals(respuesta.getRespuesta())){
                        opcionToRespuesta = opcion;
                    } 
                }
                respToDB.addOpcion(opcionToRespuesta);
                
                List<Opcion> opcionesRespuesta = PMR.get().getOpcionesRespuesta();
                Iterator<Opcion> iteratorOpcionesRespuesta = opcionesRespuesta.iterator();
                while(iteratorOpcionesRespuesta.hasNext()){
                    Opcion opcion = iteratorOpcionesRespuesta.next();
                    if(respuesta.getRespuesta().equals(opcion.getLetra())){
                        respuesta.setIsCorrect(true);
                        PMR.get().setPreguntasCorrectas(PMR.get().getPreguntasCorrectas() + 1);
                    }
                }
                
                if(PMR.get().getPreguntasCorrectas() == opcionesRespuesta.size()){
                    notaEstudiante = notaEstudiante + PMR.get().getValoracion();
                    estudianteRelacionado.get().setNota(notaEstudiante);
                }
                
                if(respuesta.getIsCorrect() == null){
                    respuesta.setIsCorrect(false);
                }
                
                estudianteRelacionado.get().addRespuesta(respToDB);
                estudianteRepository.save(estudianteRelacionado.get());
                return respuesta;
            }
        }
        return null;
    }
    
    public Double getNota(String idEstudiante) throws Exception{
        if(idEstudiante == null || idEstudiante.isBlank() || idEstudiante.isEmpty()){
            throw new Exception("Faltan datos");
        }
        else{
            Optional<Estudiante> estudiante = estudianteRepository.findById(Long.valueOf(idEstudiante));
            return estudiante.get().getNota();
        }
    }
}