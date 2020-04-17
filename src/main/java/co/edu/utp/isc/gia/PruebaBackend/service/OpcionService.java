/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Opcion;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaMultiplesRespuestas;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaUnicaRespuesta;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.OpcionRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaMultiplesRespuestasRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaUnicaRespuestaRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.OpcionDTO;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.OpcionDTOInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
public class OpcionService {
    
    private final PreguntaRepository preguntaRepository;
    private final PreguntaUnicaRespuestaRepository preguntaURRepository;
    private final PreguntaMultiplesRespuestasRepository preguntaMRRepository;
    private final OpcionRepository opcionRepository;

    @Autowired
    private final OpcionMapper opcionMapper;
    
    public Boolean addOpcion(OpcionDTO opcionDTO) throws Exception{
        Boolean opcionAdded = false;
        if(opcionDTO.getTipo_pregunta() == null || opcionDTO.getLetra() == null || opcionDTO.getDescripcion() == null || opcionDTO.getNumeroPregunta() == null || opcionDTO.getExamen_id() == null){
            throw new Exception("Faltan datos");
        }
        else{
            Long respFromRepository = preguntaRepository.findIdPregunta(String.valueOf(opcionDTO.getExamen_id()), String.valueOf(opcionDTO.getNumeroPregunta()));
            Opcion opcionToDB = opcionMapper.toOpcion(opcionDTO);
            if("Pregunta con múltiples opciones y única respuesta".equals(opcionDTO.getTipo_pregunta())){
                Optional<PreguntaUnicaRespuesta> preguntaRelated = preguntaURRepository.findById(respFromRepository);
                preguntaRelated.get().addOpcion(opcionToDB);
                preguntaURRepository.save(preguntaRelated.get());
                opcionAdded = true;
            }
            else if("Pregunta con múltiples opciones y múltiples respuestas".equals(opcionDTO.getTipo_pregunta())){
                Optional<PreguntaMultiplesRespuestas> preguntaRelated = preguntaMRRepository.findById(respFromRepository);
                preguntaRelated.get().addOpcion(opcionToDB);
                preguntaMRRepository.save(preguntaRelated.get());
                opcionAdded = true;
            }
            else{
                throw new Exception("No se pudo añadir las opciones, tipo desconocido de pregunta");
            }
        }
        return opcionAdded;
    }
    
    public Boolean addRespuesta(OpcionDTO opcionDTO) throws Exception{
        Boolean respuestaAdded = false;
        if(opcionDTO.getTipo_pregunta() == null || opcionDTO.getLetra() == null || opcionDTO.getNumeroPregunta() == null || opcionDTO.getExamen_id() == null){
            throw new Exception("Faltan datos");
        }
        else{
            Long respFromRepository = preguntaRepository.findIdPregunta(String.valueOf(opcionDTO.getExamen_id()), String.valueOf(opcionDTO.getNumeroPregunta()));
            if("Pregunta con múltiples opciones y única respuesta".equals(opcionDTO.getTipo_pregunta())){
                Optional<PreguntaUnicaRespuesta> preguntaRelated = preguntaURRepository.findById(respFromRepository);
                Iterator<Opcion> crunchifyIterator = preguntaRelated.get().getOpciones().iterator();
                Opcion ensaya = new Opcion();
                while(crunchifyIterator.hasNext()){
                    Opcion opcion = crunchifyIterator.next();
                    if(opcion.getLetra().equals(opcionDTO.getLetra())){
                        ensaya = opcion;
                    } 
                }
                preguntaRelated.get().setOpcionRespuesta(ensaya);
                preguntaURRepository.save(preguntaRelated.get());
                respuestaAdded = true;
            }
            else if("Pregunta con múltiples opciones y múltiples respuestas".equals(opcionDTO.getTipo_pregunta())){
                Optional<PreguntaMultiplesRespuestas> preguntaRelated = preguntaMRRepository.findById(respFromRepository);
                Iterator<Opcion> crunchifyIterator = preguntaRelated.get().getOpciones().iterator();
                Opcion ensaya = new Opcion();
                while(crunchifyIterator.hasNext()){
                    Opcion opcion = crunchifyIterator.next();
                    if(opcion.getLetra().equals(opcionDTO.getLetra())){
                        ensaya = opcion;
                    } 
                }
                preguntaRelated.get().addRespuesta(ensaya);
                preguntaMRRepository.save(preguntaRelated.get());
                respuestaAdded = true;
            }
            else{
                throw new Exception("No se pudo añadir la respuesta, tipo desconocido de pregunta");
            }
        }
        return respuestaAdded;
    }
    
    public List<Map<String, String>> getOpciones(String idPregunta) throws Exception{
        if(idPregunta == null || idPregunta.isBlank() || idPregunta.isEmpty()){
            throw new Exception("id Nula");
        }
        else{
            List<OpcionDTOInterface> opcionesInterface = opcionRepository.findOpcionesByIdPregunta(idPregunta);
            List<Map<String, String>> resp = new ArrayList<Map<String, String>>();
            for(OpcionDTOInterface e : opcionesInterface){
                Map<String, String> element = new HashMap<>();
                element.put("id", String.valueOf(e.getId()));
                element.put("letra", e.getLetra());
                element.put("descripcion", e.getDescripcion());
                element.put("pregunta_unica_respuesta_id", String.valueOf(e.getpregunta_unica_respuesta_id()));
                element.put("pregunta_multiples_respuestas_id", String.valueOf(e.getpregunta_multiples_respuestas_id()));
                element.put("respuesta_multiple_id", String.valueOf(e.getrespuesta_multiple_id()));
                resp.add(element);
            }
            return resp;
        }
    }
    
    public OpcionDTO getRespuesta(String idPregunta) throws Exception{
        if(idPregunta == null || idPregunta.isBlank() || idPregunta.isEmpty()){
            throw new Exception("id Nula");
        }
        else{
            Optional<PreguntaUnicaRespuesta> pre = preguntaURRepository.findById(Long.valueOf(idPregunta));
            Opcion respuesta = pre.get().getOpcionRespuesta();
            OpcionDTO resp = opcionMapper.fromOpcion(respuesta);
            resp.setNumeroPregunta(pre.get().getNumero_pregunta());
            return resp;
        }
    }
    
}
