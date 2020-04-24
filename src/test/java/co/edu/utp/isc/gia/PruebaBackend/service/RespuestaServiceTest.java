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
import co.edu.utp.isc.gia.PruebaBackend.data.entity.Profesor;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.EstudianteRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ExamenRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaMultiplesRespuestasRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaRespuestaAbiertaRepository;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.PreguntaUnicaRespuestaRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.EstudianteDTO;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.RespuestaDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
/**
 *
 * @author ahsan
 */
@SpringBootTest
public class RespuestaServiceTest {
    
    public RespuestaServiceTest() {
    }
    
    @Mock
    private EstudianteRepository estudianteRepository;
    
    @Mock
    private ExamenRepository examenRepository;
    
    @Mock
    private PreguntaRepository preguntaRepository;
    
    @Mock
    private PreguntaRespuestaAbiertaRepository preguntaRARepository;
    
    @Mock
    private PreguntaUnicaRespuestaRepository preguntaURRepository;
    
    @Mock
    private PreguntaMultiplesRespuestasRepository preguntaMRRepository;
    
    @Autowired
    private EstudianteMapper estudianteMapper;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testaddEstudiante_estudianteNullExpectedException(){
        EstudianteDTO input = null;
        
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addEstudiante(input);
        });
    }
    
    @Test
    public void testaddEstudiante_estudianteOkExpectedOk() throws Exception{
        Profesor p = new Profesor(1L, "Profe", "profe@mail.com", "123", null);
        Examen fromRepo = new Examen(1L, "Examen Prueba", 5.0, p, null);
        Optional<Examen> OptionalFromRepo = Optional.of(fromRepo);
        
        Estudiante fromRepoStudent = new Estudiante(1L, "Ensayo", "pepito@mail.com", null, fromRepo, null);
        
        when(examenRepository.findById(any(Long.class))).thenReturn(OptionalFromRepo);
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(fromRepoStudent);
        
        //Input
        EstudianteDTO input = new EstudianteDTO(null, "Ensayo", "pepito@mail.com", 1L);
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected
        EstudianteDTO expected = new EstudianteDTO(1L, "Ensayo", "pepito@mail.com", 1L);
        
        //Result
        EstudianteDTO result = instance.addEstudiante(input);
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddRespuesta_respuestaNullExpectedException(){
        RespuestaDTO input = null;
        
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        assertThrows(Exception.class, () -> {
            instance.addRespuesta(input);
        });
    }
    
    @Test
    public void testAddRespuesta_respuestaAbiertaExpectedOk() throws Exception{
        Profesor p = new Profesor(1L, "Profe", "profe@mail.com", "123", null);
        Examen fromRepo = new Examen(1L, "Examen Prueba", 5.0, p, null);
        Estudiante fromRepoStudent = new Estudiante(1L, "Ensayo", "pepito@mail.com", 0.0, fromRepo, null);
        Optional<Estudiante> OptionalFromRepo = Optional.of(fromRepoStudent);
        
        when(estudianteRepository.findById(any(Long.class))).thenReturn(OptionalFromRepo);
        
        Long idPreguntaFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(idPreguntaFromRepo);
        
        PreguntaRespuestaAbierta PRAFromRepository = new PreguntaRespuestaAbierta();
        PRAFromRepository.setId(1L);
        PRAFromRepository.setNumero_pregunta(1);
        PRAFromRepository.setValoracion(1.0);
        Optional<PreguntaRespuestaAbierta> OptionalPRA = Optional.of(PRAFromRepository);
        when(preguntaRARepository.findById(any(Long.class))).thenReturn(OptionalPRA);
        when(preguntaRARepository.save(any(PreguntaRespuestaAbierta.class))).thenReturn(null);
        
        //Input
        RespuestaDTO input = new RespuestaDTO(1L, 1, "Pregunta con respuesta abierta", "asno", 1.0, null);
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected
        RespuestaDTO expected = new RespuestaDTO(1L, 1, "Pregunta con respuesta abierta", "asno", 1.0, null);
        
        //Result
        RespuestaDTO result = instance.addRespuesta(input);
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddRespuesta_respuestaPreguntaUnicaRespuestaExpectedOk() throws Exception{
        Profesor p = new Profesor(1L, "Profe", "profe@mail.com", "123", null);
        Examen fromRepo = new Examen(1L, "Examen Prueba", 5.0, p, null);
        Estudiante fromRepoStudent = new Estudiante(1L, "Ensayo", "pepito@mail.com", 0.0, fromRepo, null);
        Optional<Estudiante> OptionalFromRepo = Optional.of(fromRepoStudent);
        
        when(estudianteRepository.findById(any(Long.class))).thenReturn(OptionalFromRepo);
        
        Long idPreguntaFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(idPreguntaFromRepo);
        
        Opcion a = new Opcion(null, "a", "Vienna");
        Opcion b = new Opcion(null, "b", "Lux");
        Opcion c = new Opcion(null, "c", "Pet");
        List<Opcion> opciones = new ArrayList<>(Arrays.asList(a,b,c));
        
        PreguntaUnicaRespuesta PURFromRepository = new PreguntaUnicaRespuesta();
        PURFromRepository.setId(1L);
        PURFromRepository.setNumero_pregunta(1);
        PURFromRepository.setValoracion(1.0);
        PURFromRepository.setOpciones(opciones);
        PURFromRepository.setOpcionRespuesta(c);
        
        Optional<PreguntaUnicaRespuesta> OptionalPUR = Optional.of(PURFromRepository);
        when(preguntaURRepository.findById(any(Long.class))).thenReturn(OptionalPUR);
        when(preguntaURRepository.save(any(PreguntaUnicaRespuesta.class))).thenReturn(null);
        
        //Input
        RespuestaDTO input = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y única respuesta", "c", 1.0, null);
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected
        RespuestaDTO expected = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y única respuesta", "c", 1.0, true);
        
        //Result
        RespuestaDTO result = instance.addRespuesta(input);
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddRespuesta_respuestaPreguntaUnicaRespuestaExpectedFalse() throws Exception{
        Profesor p = new Profesor(1L, "Profe", "profe@mail.com", "123", null);
        Examen fromRepo = new Examen(1L, "Examen Prueba", 5.0, p, null);
        Estudiante fromRepoStudent = new Estudiante(1L, "Ensayo", "pepito@mail.com", 0.0, fromRepo, null);
        Optional<Estudiante> OptionalFromRepo = Optional.of(fromRepoStudent);
        
        when(estudianteRepository.findById(any(Long.class))).thenReturn(OptionalFromRepo);
        
        Long idPreguntaFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(idPreguntaFromRepo);
        
        Opcion a = new Opcion(null, "a", "Vienna");
        Opcion b = new Opcion(null, "b", "Lux");
        Opcion c = new Opcion(null, "c", "Pet");
        List<Opcion> opciones = new ArrayList<>(Arrays.asList(a,b,c));
        
        PreguntaUnicaRespuesta PURFromRepository = new PreguntaUnicaRespuesta();
        PURFromRepository.setId(1L);
        PURFromRepository.setNumero_pregunta(1);
        PURFromRepository.setValoracion(1.0);
        PURFromRepository.setOpciones(opciones);
        PURFromRepository.setOpcionRespuesta(c);
        
        Optional<PreguntaUnicaRespuesta> OptionalPUR = Optional.of(PURFromRepository);
        when(preguntaURRepository.findById(any(Long.class))).thenReturn(OptionalPUR);
        when(preguntaURRepository.save(any(PreguntaUnicaRespuesta.class))).thenReturn(null);
        
        //Input
        RespuestaDTO input = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y única respuesta", "a", 1.0, null);
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected
        RespuestaDTO expected = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y única respuesta", "a", 1.0, false);
        
        //Result
        RespuestaDTO result = instance.addRespuesta(input);
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddRespuesta_respuestaPreguntaMultiplesRespuestasExpectedOk() throws Exception{
        Profesor p = new Profesor(1L, "Profe", "profe@mail.com", "123", null);
        Examen fromRepo = new Examen(1L, "Examen Prueba", 5.0, p, null);
        Estudiante fromRepoStudent = new Estudiante(1L, "Ensayo", "pepito@mail.com", 0.0, fromRepo, null);
        Optional<Estudiante> OptionalFromRepo = Optional.of(fromRepoStudent);
        
        when(estudianteRepository.findById(any(Long.class))).thenReturn(OptionalFromRepo);
        
        Long idPreguntaFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(idPreguntaFromRepo);
        
        Opcion a = new Opcion(null, "a", "Vienna");
        Opcion b = new Opcion(null, "b", "Lux");
        Opcion c = new Opcion(null, "c", "Pet");
        List<Opcion> opciones = new ArrayList<>(Arrays.asList(a,b,c));
        List<Opcion> opcionesRespuesta = new ArrayList<>(Arrays.asList(a));
        
        PreguntaMultiplesRespuestas PMRFromRepository = new PreguntaMultiplesRespuestas();
        PMRFromRepository.setId(1L);
        PMRFromRepository.setNumero_pregunta(1);
        PMRFromRepository.setValoracion(1.0);
        PMRFromRepository.setPreguntasCorrectas(0);
        PMRFromRepository.setOpciones(opciones);
        PMRFromRepository.setOpcionesRespuesta(opcionesRespuesta);
        
        Optional<PreguntaMultiplesRespuestas> OptionalPMR = Optional.of(PMRFromRepository);
        when(preguntaMRRepository.findById(any(Long.class))).thenReturn(OptionalPMR);
        when(preguntaMRRepository.save(any(PreguntaMultiplesRespuestas.class))).thenReturn(null);
        
        //Input
        RespuestaDTO input = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y múltiples respuestas", "a", 1.0, null);
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected
        RespuestaDTO expected = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y múltiples respuestas", "a", 1.0, true);
        
        //Result
        RespuestaDTO result = instance.addRespuesta(input);
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddRespuesta_respuestaPreguntaMultiplesRespuestasExpectedFalse() throws Exception{
        Profesor p = new Profesor(1L, "Profe", "profe@mail.com", "123", null);
        Examen fromRepo = new Examen(1L, "Examen Prueba", 5.0, p, null);
        Estudiante fromRepoStudent = new Estudiante(1L, "Ensayo", "pepito@mail.com", 0.0, fromRepo, null);
        Optional<Estudiante> OptionalFromRepo = Optional.of(fromRepoStudent);
        
        when(estudianteRepository.findById(any(Long.class))).thenReturn(OptionalFromRepo);
        
        Long idPreguntaFromRepo = 1L;
        when(preguntaRepository.findIdPregunta(any(String.class), any(String.class))).thenReturn(idPreguntaFromRepo);
        
        Opcion a = new Opcion(null, "a", "Vienna");
        Opcion b = new Opcion(null, "b", "Lux");
        Opcion c = new Opcion(null, "c", "Pet");
        List<Opcion> opciones = new ArrayList<>(Arrays.asList(a,b,c));
        List<Opcion> opcionesRespuesta = new ArrayList<>(Arrays.asList(a));
        
        PreguntaMultiplesRespuestas PMRFromRepository = new PreguntaMultiplesRespuestas();
        PMRFromRepository.setId(1L);
        PMRFromRepository.setNumero_pregunta(1);
        PMRFromRepository.setValoracion(1.0);
        PMRFromRepository.setPreguntasCorrectas(0);
        PMRFromRepository.setOpciones(opciones);
        PMRFromRepository.setOpcionesRespuesta(opcionesRespuesta);
        
        Optional<PreguntaMultiplesRespuestas> OptionalPMR = Optional.of(PMRFromRepository);
        when(preguntaMRRepository.findById(any(Long.class))).thenReturn(OptionalPMR);
        when(preguntaMRRepository.save(any(PreguntaMultiplesRespuestas.class))).thenReturn(null);
        
        //Input
        RespuestaDTO input = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y múltiples respuestas", "b", 1.0, null);
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected
        RespuestaDTO expected = new RespuestaDTO(1L, 1, "Pregunta con múltiples opciones y múltiples respuestas", "b", 1.0, false);
        
        //Result
        RespuestaDTO result = instance.addRespuesta(input);
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testgetNota_idEstudianteNullExpectedException(){
        String input = null;
        
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        assertThrows(Exception.class, () -> {
            instance.getNota(input);
        });
    }
    
    @Test
    public void testgetNota_idEstudianteOkExpectedOk() throws Exception{
        Estudiante estudianteFromRepo = new Estudiante(1L, "Carlos", "black@utp.edu.co", 0.0, null, null);
        Optional<Estudiante> optionalOfStudentFromRepo = Optional.of(estudianteFromRepo);
        when(estudianteRepository.findById(any(Long.class))).thenReturn(optionalOfStudentFromRepo);
        
        //Input
        String input = "1";
        
        //Target
        RespuestaService instance = new RespuestaService(estudianteRepository, examenRepository, preguntaRepository, preguntaRARepository, preguntaURRepository, preguntaMRRepository, estudianteMapper);
        
        //Expected  
        Double expected = 0.0;
        
        //Result
        Double result = instance.getNota(input);
        
        assertEquals(expected, result);
    }
    
    
}
