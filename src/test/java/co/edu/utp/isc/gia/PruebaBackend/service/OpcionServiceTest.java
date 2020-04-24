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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

/**
 *
 * @author ahsan
 */
@SpringBootTest
public class OpcionServiceTest {
    
    public OpcionServiceTest() {
    }
    
    @Mock
    private PreguntaRepository preguntaRepository;
    
    @Mock
    private PreguntaUnicaRespuestaRepository preguntaURRepository;
    
    @Mock
    private PreguntaMultiplesRespuestasRepository preguntaMRRepository;
    
    @Mock
    private OpcionRepository opcionRepository;

    @Autowired
    private OpcionMapper opcionMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testAddOpcion_OpcionNullExpectedException(){
        OpcionDTO opcionDTOInput = new OpcionDTO(null, null, null, null, null, null);
        
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addOpcion(opcionDTOInput);
        });
    }
    
    @Test
    public void testAddOpcion_OpcionTipoPreguntaUnicaRespuestaExpectedOk() throws Exception{
        Long expectedFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(expectedFromRepo);
        
        PreguntaUnicaRespuesta PURFromRepository = new PreguntaUnicaRespuesta();
        PURFromRepository.setId(1L);
        PURFromRepository.setNumero_pregunta(1);
        PURFromRepository.setValoracion(1.3);
        
        Optional<PreguntaUnicaRespuesta> OptionalPUR = Optional.of(PURFromRepository);
        
        when(preguntaURRepository.findById(any(Long.class))).thenReturn(OptionalPUR);
        when(preguntaURRepository.save(any(PreguntaUnicaRespuesta.class))).thenReturn(null);
 
        //Input
        OpcionDTO opcionDTOInput = new OpcionDTO(null, "a", "Vienna", 1, "Pregunta con múltiples opciones y única respuesta", 1L);
        
        //Target
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        //Expected
        Boolean exp = true;
        
        //Test
        Boolean result = instance.addOpcion(opcionDTOInput);
        
        assertEquals(exp, result);
        
    }
    
    @Test
    public void testAddOpcion_OpcionTipoPreguntaMultiplesRespuestasExpectedOk() throws Exception{
        Long expectedFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(expectedFromRepo);
        
        PreguntaMultiplesRespuestas PMRFromRepository = new PreguntaMultiplesRespuestas();
        PMRFromRepository.setId(1L);
        PMRFromRepository.setNumero_pregunta(1);
        PMRFromRepository.setValoracion(1.3);
        
        Optional<PreguntaMultiplesRespuestas> OptionalPUR = Optional.of(PMRFromRepository);
        
        when(preguntaMRRepository.findById(any(Long.class))).thenReturn(OptionalPUR);
        when(preguntaMRRepository.save(any(PreguntaMultiplesRespuestas.class))).thenReturn(null);
 
        //Input
        OpcionDTO opcionDTOInput = new OpcionDTO(null, "a", "Vienna", 1, "Pregunta con múltiples opciones y múltiples respuestas", 1L);
        
        //Target
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        //Expected
        Boolean exp = true;
        
        //Test
        Boolean result = instance.addOpcion(opcionDTOInput);
        
        assertEquals(exp, result);
    }
    
    @Test
    public void testAddOpcion_OpcionTipoDesconocidoExpectedException(){
        OpcionDTO opcionDTOInput = new OpcionDTO(null, "a", "Vienna", 1, "abc", 1L);
        
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addOpcion(opcionDTOInput);
        });
    }
    
    @Test
    public void testaddRespuesta_OpcionNullExpectedException(){
        OpcionDTO opcionDTOInput = new OpcionDTO(null, null, null, null, null, null);
        
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addRespuesta(opcionDTOInput);
        });
    }
    
    @Test
    public void testaddRespuesta_OpcionTipoPreguntaUnicaRespuestaExpectedOk() throws Exception{
        Long expectedFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(expectedFromRepo);
        
        PreguntaUnicaRespuesta PURFromRepository = new PreguntaUnicaRespuesta();
        PURFromRepository.setId(1L);
        PURFromRepository.setNumero_pregunta(1);
        PURFromRepository.setValoracion(1.3);
        
        Opcion a = new Opcion(1L, "a", "Vienna");
        Opcion b = new Opcion(1L, "b", "Austria");
        Opcion c = new Opcion(1L, "c", "Chernovyl");
        Opcion d = new Opcion(1L, "d", "Luxemburg");
        List<Opcion> opcionesToPUR = new ArrayList<Opcion>(Arrays.asList(a,b,c,d));
        PURFromRepository.setOpciones(opcionesToPUR);
        
        
        Optional<PreguntaUnicaRespuesta> OptionalPUR = Optional.of(PURFromRepository);
        
        when(preguntaURRepository.findById(any(Long.class))).thenReturn(OptionalPUR);
        when(preguntaURRepository.save(any(PreguntaUnicaRespuesta.class))).thenReturn(null);
        
        //Input
        OpcionDTO opcionDTOInput = new OpcionDTO(null, "a", "Vienna", 1, "Pregunta con múltiples opciones y única respuesta", 1L);
        
        //Target
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        //Expected
        Boolean exp = true;
        
        //Test
        Boolean result = instance.addRespuesta(opcionDTOInput);
        
        assertEquals(exp, result);
    }
    
    @Test
    public void testaddRespuesta_OpcionTipoPreguntaMultiplesRespuestasExpectedOk() throws Exception{
        Long expectedFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(expectedFromRepo);
        
        PreguntaMultiplesRespuestas PMRFromRepository = new PreguntaMultiplesRespuestas();
        PMRFromRepository.setId(1L);
        PMRFromRepository.setNumero_pregunta(1);
        PMRFromRepository.setValoracion(1.3);
        
        Opcion a = new Opcion(1L, "a", "Vienna");
        Opcion b = new Opcion(1L, "b", "Austria");
        Opcion c = new Opcion(1L, "c", "Chernovyl");
        Opcion d = new Opcion(1L, "d", "Luxemburg");
        List<Opcion> opcionesToPUR = new ArrayList<Opcion>(Arrays.asList(a,b,c,d));
        PMRFromRepository.setOpciones(opcionesToPUR);
        
        
        Optional<PreguntaMultiplesRespuestas> OptionalPMR = Optional.of(PMRFromRepository);
        
        when(preguntaMRRepository.findById(any(Long.class))).thenReturn(OptionalPMR);
        when(preguntaMRRepository.save(any(PreguntaMultiplesRespuestas.class))).thenReturn(null);
        
        //Input
        OpcionDTO opcionDTOInput = new OpcionDTO(null, "a", "Vienna", 1, "Pregunta con múltiples opciones y múltiples respuestas", 1L);
        
        //Target
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        //Expected
        Boolean exp = true;
        
        //Test
        Boolean result = instance.addRespuesta(opcionDTOInput);
        
        assertEquals(exp, result);
    }
    
    @Test
    public void testaddRespuesta_OpcionTipoDesconocidoExpectedException(){
        OpcionDTO opcionDTOInput = new OpcionDTO(null, "a", "Vienna", 1, "abc", 1L);
        
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addRespuesta(opcionDTOInput);
        });
    }
    
    @Test
    public void testgetOpciones_idPreguntaNullExpectedException(){
        String input = null;
        
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        assertThrows(Exception.class, () -> {
            instance.getOpciones(input);
        });
    }
    
    @Test
    public void testgetOpciones_idPreguntaOkExpectedOk() throws Exception{
        List<OpcionDTOInterface> opcionesInterface = new ArrayList<>();
        
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        OpcionDTOInterface projection = factory.createProjection(OpcionDTOInterface.class);
        
        projection.setID(null);
        projection.setLetra("a");
        projection.setDescripcion("Vienna");
        projection.setpregunta_unica_respuesta_id(1L);
        projection.setpregunta_multiples_respuestas_id(null);
        projection.setrespuesta_multiple_id(null);
        
        opcionesInterface.add(projection);
        when(opcionRepository.findOpcionesByIdPregunta(any(String.class))).thenReturn(opcionesInterface);
        
        //Input
        String input = "1";
        
        //Target
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        //Expected
        List<Map<String, String>> expected = new ArrayList<Map<String, String>>();
        Map<String, String> element = new HashMap<>();
        element.put("id", null);
        element.put("letra","a");
        element.put("descripcion", "Vienna");
        element.put("pregunta_unica_respuesta_id", String.valueOf(1L));
        element.put("pregunta_multiples_respuestas_id", null);
        element.put("respuesta_multiple_id", null);
        expected.add(element);
        
        List<Map<String, String>> result = instance.getOpciones(input);
        
        Iterator<Map<String, String>> it1 = expected.iterator();
        Iterator<Map<String, String>> it2 = result.iterator();
        
        while(it1.hasNext() && it2.hasNext()){
            Map<String, String> a = it1.next();
            Map<String, String> b = it2.next();
            Iterator<Entry<String, String>> iter1 = a.entrySet().iterator();
            Iterator<Entry<String, String>> iter2 = b.entrySet().iterator();
            while(iter1.hasNext() && iter2.hasNext()){
                Entry<String, String> e1 = iter1.next();
                Entry<String, String> e2 = iter2.next();
                assertEquals(e1.toString(), e2.toString());
            }
        }
        
    }
    
    @Test
    public void getRespuesta_idPreguntaNullExpectedException(){
        String input = null;
        
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        assertThrows(Exception.class, () -> {
            instance.getRespuesta(input);
        });
    }
    
    @Test
    public void getRespuesta_idPreguntaOkExpectedOk() throws Exception{
        PreguntaUnicaRespuesta PURFromRepository = new PreguntaUnicaRespuesta();
        PURFromRepository.setId(1L);
        PURFromRepository.setNumero_pregunta(1);
        PURFromRepository.setValoracion(1.3);
        Opcion a = new Opcion(1L, "a", "Vienna");
        PURFromRepository.setOpcionRespuesta(a);
        
        Optional<PreguntaUnicaRespuesta> OptionalPUR = Optional.of(PURFromRepository);
        
        when(preguntaURRepository.findById(any(Long.class))).thenReturn(OptionalPUR);
        
        //Input
        String input = "1";
        
        //Target
        OpcionService instance = new OpcionService(preguntaRepository, preguntaURRepository, preguntaMRRepository, opcionRepository, opcionMapper);
        
        //Expected
        OpcionDTO expected = new OpcionDTO(1L, "a", "Vienna", 1, null, null);
        
        //Result
        OpcionDTO result = instance.getRespuesta(input);
        
        assertEquals(expected, result);
    }
}
