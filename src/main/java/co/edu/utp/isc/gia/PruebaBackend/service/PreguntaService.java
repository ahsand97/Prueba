/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Examen;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.Pregunta;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaMultiplesRespuestas;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaRespuestaAbierta;
import co.edu.utp.isc.gia.PruebaBackend.data.entity.PreguntaUnicaRespuesta;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ExamenRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaMultiplesRespuestasRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRespuestaAbiertaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaUnicaRespuestaRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.PreguntaDTO;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.PreguntaDTOInterface;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ahsan
 */
@Service
@RequiredArgsConstructor
public class PreguntaService {
    
    private final PreguntaRepository preguntaRepository;
    private final PreguntaRespuestaAbiertaRepository preguntaRARepository;
    private final PreguntaUnicaRespuestaRepository preguntaURRepository;
    private final PreguntaMultiplesRespuestasRepository preguntaMRRepository;
    
    private final ExamenRepository examenRepository;
    
    @Autowired
    private final PreguntaMapper preguntaMapper;
    
    public Optional<?> PreguntaToChild(Pregunta P, Integer tipoPregunta) throws Exception{
        Integer numeroPregunta = P.getNumero_pregunta();
        String descripcion = P.getDescripcion();
        Double valoracion = P.getValoracion();
        Examen examen = P.getExamen();

        switch (tipoPregunta) {
            case 1:
            {
                PreguntaRespuestaAbierta pre = new PreguntaRespuestaAbierta();
                pre.setNumero_pregunta(numeroPregunta);
                pre.setDescripcion(descripcion);
                pre.setValoracion(valoracion);
                pre.setExamen(examen);
                return Optional.of(pre);
            }
            case 2:
            {
                PreguntaUnicaRespuesta pre = new PreguntaUnicaRespuesta();
                pre.setNumero_pregunta(numeroPregunta);
                pre.setDescripcion(descripcion);
                pre.setValoracion(valoracion);
                pre.setExamen(examen);
                return Optional.of(pre);
            }
            case 3:
            {
                PreguntaMultiplesRespuestas pre = new PreguntaMultiplesRespuestas();
                pre.setNumero_pregunta(numeroPregunta);
                pre.setDescripcion(descripcion);
                pre.setValoracion(valoracion);
                pre.setExamen(examen);
                return Optional.of(pre);
            }
            default:
                throw new Exception("Tipo no reconocido");
        }
    }
    
    public PreguntaDTO addPregunta(PreguntaDTO preguntaDTO) throws Exception{
        if(preguntaDTO.getDescripcion() == null|| preguntaDTO.getNumero_pregunta() == null || preguntaDTO.getDtype() == null || preguntaDTO.getValoracion() == null || preguntaDTO.getExamen_id() == null){
            throw new Exception("Faltan datos");  
        }
        else{
            Optional<Examen> examenFK = examenRepository.findById(preguntaDTO.getExamen_id());
            Pregunta preguntaToDB = preguntaMapper.toPregunta(preguntaDTO);
            preguntaToDB.setExamen(examenFK.get());
            switch (preguntaDTO.getDtype()) {
                case "Pregunta con respuesta abierta":
                {
                    PreguntaRespuestaAbierta pre = (PreguntaRespuestaAbierta) PreguntaToChild(preguntaToDB, 1).get();
                    PreguntaRespuestaAbierta respFromDB  = preguntaRARepository.save(pre);
                    Pregunta castToParent = (Pregunta) respFromDB;
                    PreguntaDTO resp = preguntaMapper.fromPregunta(castToParent);
                    resp.setDtype("Pregunta con respuesta abierta");
                    resp.setExamen_id(examenFK.get().getId());
                    return resp;
                    
                }
                case "Pregunta con múltiples opciones y única respuesta":
                {
                    PreguntaUnicaRespuesta pre = (PreguntaUnicaRespuesta) PreguntaToChild(preguntaToDB, 2).get();
                    PreguntaUnicaRespuesta respFromDB = preguntaURRepository.save(pre);
                    Pregunta castToParent = (Pregunta) respFromDB;
                    PreguntaDTO resp = preguntaMapper.fromPregunta(castToParent);
                    resp.setDtype("Pregunta con múltiples opciones y única respuesta");
                    resp.setExamen_id(examenFK.get().getId());
                    return resp;
                }
                case "Pregunta con múltiples opciones y múltiples respuestas":
                {
                    PreguntaMultiplesRespuestas pre = (PreguntaMultiplesRespuestas) PreguntaToChild(preguntaToDB, 3).get();
                    PreguntaMultiplesRespuestas respFromDB = preguntaMRRepository.save(pre);
                    Pregunta castToParent = (Pregunta) respFromDB;
                    PreguntaDTO resp = preguntaMapper.fromPregunta(castToParent);
                    resp.setDtype("Pregunta con múltiples opciones y múltiples respuestas");
                    resp.setExamen_id(examenFK.get().getId());
                    return resp;
                }
                default:
                    throw new Exception("Tipo no encontrado");
            }
        }
    }
    
    public void addImagenToPregunta(String Id, String numeroPregunta, MultipartFile imagen) throws Exception{
        if(imagen == null || Id == null || numeroPregunta == null){
            throw new Exception("Faltan datos");
        }
        else{
            Map<String,BigInteger> preguntaFromDB = preguntaRepository.findIdAndTipoPregunta(Id, numeroPregunta);
            Long idPreguntaBase = preguntaFromDB.get("id").longValue();
            String tipoPreguntaBase = String.valueOf(preguntaFromDB.get("tipo_pregunta"));
            
            if(idPreguntaBase == null){
                throw new Exception("No se encuentra la pregunta");
            }
            else{
                if("respuesta_abierta".equals(tipoPreguntaBase)){
                    Optional<PreguntaRespuestaAbierta> preguntaToSetImage = preguntaRARepository.findById(idPreguntaBase);
                    preguntaToSetImage.get().setImagen(imagen.getBytes());
                    preguntaRARepository.save(preguntaToSetImage.get());
                }
                else if("unica_respuesta".equals(tipoPreguntaBase)){
                    Optional<PreguntaUnicaRespuesta> preguntaToSetImage = preguntaURRepository.findById(idPreguntaBase);
                    preguntaToSetImage.get().setImagen(imagen.getBytes());
                    preguntaURRepository.save(preguntaToSetImage.get());
                }
                else if("multiples_respuestas".equals(tipoPreguntaBase)){
                    Optional<PreguntaMultiplesRespuestas> preguntaToSetImage = preguntaMRRepository.findById(idPreguntaBase);
                    preguntaToSetImage.get().setImagen(imagen.getBytes());
                    preguntaMRRepository.save(preguntaToSetImage.get());
                }
            }
        }
    }
    
    public List<PreguntaDTO> getPreguntas(String idExamen) throws Exception{
        if(idExamen == null || idExamen.isBlank() || idExamen.isEmpty()){
            throw new Exception("Id nula");
        }
        else{
            List<PreguntaDTOInterface> preguntasInterface = preguntaRepository.findPreguntasByIdExamen(Long.valueOf(idExamen));
            List<PreguntaDTO> preguntasResp = new ArrayList<>();
            for(PreguntaDTOInterface e : preguntasInterface){
                PreguntaDTO p = new PreguntaDTO();
                p.setId(e.getid());
                p.setNumero_pregunta(e.getnumero_pregunta());
                p.setDescripcion(e.getdescripcion());
                p.setImagen(e.getimagen());
                p.setValoracion(e.getvaloracion());
                p.setExamen_id(e.getexamen_id());
                p.setDtype(e.gettipo_pregunta());
                preguntasResp.add(p);
            }
            return preguntasResp;
        }
    }
}


