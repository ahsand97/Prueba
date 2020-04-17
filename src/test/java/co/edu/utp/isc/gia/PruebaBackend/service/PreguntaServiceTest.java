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
import org.springframework.mock.web.MockMultipartFile ;
import org.springframework.web.multipart.MultipartFile;



/**
 *
 * @author ahsan
 */
@SpringBootTest
public class PreguntaServiceTest {
    
    public PreguntaServiceTest() {
    }
    
    @Mock
    private PreguntaRepository preguntaRepository;
    
    @Mock
    private PreguntaRespuestaAbiertaRepository preguntaRARepository;
    
    @Mock
    private PreguntaUnicaRespuestaRepository preguntaURRepository;
    
    @Mock
    private PreguntaMultiplesRespuestasRepository preguntaMRRepository;
    
    @Mock
    private ExamenRepository examenRepository;
    
    @Autowired
    private PreguntaMapper preguntaMapper;
    
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPreguntaToChildPreguntaOkTipo1ExpOk() throws Exception {
        Pregunta P = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        Integer tipoPregunta = 1;
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        
        PreguntaRespuestaAbierta result = (PreguntaRespuestaAbierta) instance.PreguntaToChild(P, tipoPregunta).get();

        assertEquals(result.getNumero_pregunta(), P.getNumero_pregunta());
        assertEquals(result.getDescripcion(), P.getDescripcion());
        assertEquals(result.getValoracion(), P.getValoracion());
    }
    
    @Test
    public void testPreguntaToChildPreguntaOkTipo2ExpOk() throws Exception {
        Pregunta P = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        Integer tipoPregunta = 2;
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        
        PreguntaUnicaRespuesta result = (PreguntaUnicaRespuesta) instance.PreguntaToChild(P, tipoPregunta).get();

        assertEquals(result.getNumero_pregunta(), P.getNumero_pregunta());
        assertEquals(result.getDescripcion(), P.getDescripcion());
        assertEquals(result.getValoracion(), P.getValoracion());
    }
    
    @Test
    public void testPreguntaToChildPreguntaOkTipo3ExpOk() throws Exception {
        Pregunta P = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        Integer tipoPregunta = 3;
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        
        PreguntaMultiplesRespuestas result = (PreguntaMultiplesRespuestas) instance.PreguntaToChild(P, tipoPregunta).get();

        assertEquals(result.getNumero_pregunta(), P.getNumero_pregunta());
        assertEquals(result.getDescripcion(), P.getDescripcion());
        assertEquals(result.getValoracion(), P.getValoracion());
    }
    
    @Test
    public void testPreguntaToChildPreguntaOkTipoNullExpException() {
        Pregunta P = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        Integer tipoPregunta = 5;
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        assertThrows(Exception.class, () -> {
            instance.PreguntaToChild(P, tipoPregunta);
        });
    }

    @Test
    public void testAddPreguntaPreguntaNullExpException(){
        PreguntaDTO preguntaDTOToInput = new PreguntaDTO();
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addPregunta(preguntaDTOToInput);
        });
    }
    
    @Test
    public void testAddPreguntaPreguntaOkDtypeNullExpException() throws Exception {
        Examen examen = new Examen(1L, "Exámen ensayo 1", 5.0, null, null);
        Optional<Examen> examenFromRepo = Optional.of(examen);
        when(examenRepository.findById(any(Long.class))).thenReturn(examenFromRepo);
        
        PreguntaDTO preguntaDTOToInput = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, null, 22L);
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addPregunta(preguntaDTOToInput);
        });
    }
    
    @Test
    public void testAddPreguntaPreguntaOkDtype1ExpOk() throws Exception {
        Examen examen = new Examen(1L, "Exámen ensayo 1", 5.0, null, null);
        Optional<Examen> examenFromRepo = Optional.of(examen);
        when(examenRepository.findById(any(Long.class))).thenReturn(examenFromRepo);
        
        Pregunta expected = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        PreguntaRespuestaAbierta pRA = new PreguntaRespuestaAbierta();
        pRA.setId(expected.getId());
        pRA.setNumero_pregunta(expected.getNumero_pregunta());
        pRA.setDescripcion(expected.getDescripcion());
        pRA.setValoracion(expected.getValoracion());
        
        when(preguntaRARepository.save(any(PreguntaRespuestaAbierta.class))).thenReturn(pRA);
        
        //Input
        PreguntaDTO preguntaDTOToInput = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta con respuesta abierta", 1L);
        
        //Target
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        //Expected
        PreguntaDTO expResult = new PreguntaDTO(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, "Pregunta con respuesta abierta", 1L);
        
        //Result
        PreguntaDTO result = instance.addPregunta(preguntaDTOToInput);
        
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNumero_pregunta(), result.getNumero_pregunta());
        assertEquals(expResult.getDescripcion(), result.getDescripcion());
        assertEquals(expResult.getValoracion(), result.getValoracion());
        assertEquals(expResult.getDtype(), result.getDtype());
        assertEquals(expResult.getExamen_id(), result.getExamen_id());
    }
    
    @Test
    public void testAddPreguntaPreguntaOkDtype2ExpOk() throws Exception {
        Examen examen = new Examen(1L, "Exámen ensayo 1", 5.0, null, null);
        Optional<Examen> examenFromRepo = Optional.of(examen);
        when(examenRepository.findById(any(Long.class))).thenReturn(examenFromRepo);
        
        Pregunta expected = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        PreguntaUnicaRespuesta pUR = new PreguntaUnicaRespuesta();
        pUR.setId(expected.getId());
        pUR.setNumero_pregunta(expected.getNumero_pregunta());
        pUR.setDescripcion(expected.getDescripcion());
        pUR.setValoracion(expected.getValoracion());
        
        when(preguntaURRepository.save(any(PreguntaUnicaRespuesta.class))).thenReturn(pUR);
        
        //Input
        PreguntaDTO preguntaDTOToInput = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta con múltiples opciones y única respuesta", 1L);
        
        //Target
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        //Expected
        PreguntaDTO expResult = new PreguntaDTO(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, "Pregunta con múltiples opciones y única respuesta", 1L);
        
        //Result
        PreguntaDTO result = instance.addPregunta(preguntaDTOToInput);
        
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNumero_pregunta(), result.getNumero_pregunta());
        assertEquals(expResult.getDescripcion(), result.getDescripcion());
        assertEquals(expResult.getValoracion(), result.getValoracion());
        assertEquals(expResult.getDtype(), result.getDtype());
        assertEquals(expResult.getExamen_id(), result.getExamen_id());
    }
    
    @Test
    public void testAddPreguntaPreguntaOkDtype3ExpOk() throws Exception {
        Examen examen = new Examen(1L, "Exámen ensayo 1", 5.0, null, null);
        Optional<Examen> examenFromRepo = Optional.of(examen);
        when(examenRepository.findById(any(Long.class))).thenReturn(examenFromRepo);
        
        Pregunta expected = new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null);
        PreguntaMultiplesRespuestas pMR = new PreguntaMultiplesRespuestas();
        pMR.setId(expected.getId());
        pMR.setNumero_pregunta(expected.getNumero_pregunta());
        pMR.setDescripcion(expected.getDescripcion());
        pMR.setValoracion(expected.getValoracion());
        
        when(preguntaMRRepository.save(any(PreguntaMultiplesRespuestas.class))).thenReturn(pMR);
        
        //Input
        PreguntaDTO preguntaDTOToInput = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta con múltiples opciones y múltiples respuestas", 1L);
        
        //Target
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        //Expected
        PreguntaDTO expResult = new PreguntaDTO(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, "Pregunta con múltiples opciones y múltiples respuestas", 1L);
        
        //Result
        PreguntaDTO result = instance.addPregunta(preguntaDTOToInput);
        
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getNumero_pregunta(), result.getNumero_pregunta());
        assertEquals(expResult.getDescripcion(), result.getDescripcion());
        assertEquals(expResult.getValoracion(), result.getValoracion());
        assertEquals(expResult.getDtype(), result.getDtype());
        assertEquals(expResult.getExamen_id(), result.getExamen_id());
    }
    
    @Test
    public void testAddPreguntaPreguntaOkDtypeDesconocidoExpException() {
        Examen examen = new Examen(1L, "Exámen ensayo 1", 5.0, null, null);
        Optional<Examen> examenFromRepo = Optional.of(examen);
        when(examenRepository.findById(any(Long.class))).thenReturn(examenFromRepo);
        
        //Input
        PreguntaDTO preguntaDTOToInput = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta de relaciones", 1L);
        
        //Target
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addPregunta(preguntaDTOToInput);
        });
    }
    
    
    @Test
    public void testAddImagenToPreguntaBodyNullExpectException() {
        String Id = null;
        String numeroPregunta = null;
        MultipartFile imagen = null;
        
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addImagenToPregunta(Id, numeroPregunta, imagen);
        });
    }
    /*
    @Test
    public void testAddImagenToPreguntaBodyOkResultOk() throws Exception {
        Optional<Pregunta> expectedFromRepository = Optional.of(new Pregunta(1L, 1, "¿Cuál es la capital de Colombia?", null, 5.0, null));
        when(preguntaRepository.findIdAndTipoPregunta(any(String.class),any(String.class))).thenReturn(expectedFromRepository.get());
        when(preguntaRepository.save(any(Pregunta.class))).thenReturn(expectedFromRepository.get());
        //Input
        String Id = "15";
        String numeroPregunta = "1";
        byte[] content = null;
        MockMultipartFile imagen = new MockMultipartFile("ImagenPrueba", content);
        
        //Target
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        instance.addImagenToPregunta(Id, numeroPregunta, imagen);
    }
    */
    @Test
    public void testAddImagenToPreguntaBodyOkNoSeEncuentraPreguntaExpectedException() throws Exception {
        when(preguntaRepository.findById(any(Long.class))).thenReturn(null);
        
        //Input
        String Id = "15";
        String numeroPregunta = "1";
        byte[] content = null;
        MockMultipartFile imagen = new MockMultipartFile("ImagenPrueba", content);
        
        //Target
        PreguntaService instance = new PreguntaService(preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, examenRepository, preguntaMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addImagenToPregunta(Id, numeroPregunta, imagen);
        });
    }
}
