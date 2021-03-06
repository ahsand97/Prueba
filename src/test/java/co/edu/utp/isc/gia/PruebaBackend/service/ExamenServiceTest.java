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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class ExamenServiceTest {
    
    public ExamenServiceTest() {
    }
    
    @Autowired
    private ExamenMapper examenMapper;
    
    @Mock
    private ExamenRepository examenRepository;
    
    @Mock
    private PreguntaRepository preguntaRepository;
    
    @Mock
    private ProfesorRepository profesorRepository;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetExamenes_idProfesorOkResultOk() throws Exception {
        Examen examen = new Examen(1L, "Exámen matemáticas", 5.0, null, null);
        Examen examen2 = new Examen(2L, "Exámen electricidad", 5.0, null, null);
        Examen examen3 = new Examen(3L, "Exámen inglés", 5.0, null, null);
        Examen examen4 = new Examen(4L, "Exámen español", 5.0, null, null);
        Examen examen5 = new Examen(5L, "Exámen ingeniería de software", 5.0, null, null);
        List<Examen> examenes = new ArrayList<>();
        examenes.addAll(Arrays.asList(examen, examen2, examen3, examen4, examen5));
        
        Iterable<Examen> iterableExamenes = () -> {
            return examenes.iterator();
        };
        
        when(examenRepository.findAllExamenesByIdProfesor(any(String.class))).thenReturn(iterableExamenes);
        when(preguntaRepository.findCantidadPreguntasByIdExamen(any(Long.class))).thenReturn(0);
       
        //Input
        String idProfesor = "1";
        
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected
        Iterable<Examen> expResult = () -> {
            return examenes.iterator();
        };
        
        //Test
        List<ExamenDTO> result = instance.getExamenes(idProfesor);
        
        Iterator<Examen> it1 = expResult.iterator();
        Iterator<ExamenDTO> it2 = result.iterator();

        //Validation
        while(it1.hasNext() && it2.hasNext()){
            Examen a = it1.next();
            ExamenDTO b = it2.next();
            assertEquals(a.getId(), b.getId());
            assertEquals(a.getDescripcion(), b.getDescripcion());
            assertEquals(a.getNota_maxima(), b.getNota_maxima());
        }
    }

    @Test
    public void testGetExamenes_idProfesorNullResultException(){
        //Input
        String input = null;
       
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        try {
            //Expected exception
            //Test
            //Validation
            List<ExamenDTO> result = instance.getExamenes(input);
        } catch (Exception ex) {
            Logger.getLogger(ProfesorServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddExamen_BodyOkResultOk() throws Exception{
        Examen expectedFromDB = new Examen(1L, "Exámen prueba 1", 5.0, null, null);
        Optional<Profesor> profesorExpectedFromDB = Optional.of(new Profesor(1L, "Cesar Augusto Díaz", "black@utp.edu.co", "1234", null));
        when(examenRepository.save(any(Examen.class))).thenReturn(expectedFromDB);
        when(profesorRepository.findById(any(Long.class))).thenReturn(profesorExpectedFromDB);
        
        //Input
        ExamenDTO examenDTOtoInput = new ExamenDTO(null, "Exámen prueba 1", 5.0, 10, 1L);
        
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected
        ExamenDTO expResult = new ExamenDTO(1L, "Exámen prueba 1", 5.0, null, 1L);
        
        //Test
        ExamenDTO result = instance.addExamen(examenDTOtoInput);
        
        //Validation
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getDescripcion(), result.getDescripcion());
        assertEquals(expResult.getNota_maxima(), result.getNota_maxima());
        assertEquals(expResult.getProfesor_id(), result.getProfesor_id());
    }
    
    @Test
    public void testAddExamen_BodyNullResultException(){
        //Input
        ExamenDTO input = new ExamenDTO();
       
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected exception
        assertThrows(Exception.class, () -> {
            instance.addExamen(input);
        });
    }
    
    @Test
    public void testAddExamen_BodyEmptyResultException(){
        //Input
        ExamenDTO input = new ExamenDTO(null, "", 1.2, null, 1L);
       
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected exception
        assertThrows(Exception.class, () -> {
            instance.addExamen(input);
        });
    }
    
    @Test
    public void testDeleteExamen_BodyNullOrEmptyResultException(){
        //Input
        String input = null;
       
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected exception
        assertThrows(Exception.class, () -> {
            instance.deleteExamen(input);
        });
    }
    
    @Test
    public void testDeleteExamen_BodyOkResultNoseEncuentraExamen() throws Exception{
        
        
        when(examenRepository.findById(any(Long.class))).thenReturn(null);
        
        //Input
        String input = "1";
        
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
 
        
        //Expected exception
        Throwable exception = assertThrows(Exception.class, () -> instance.deleteExamen(input));
        
        assertEquals("No existe el exámen", exception.getMessage());
    }
    
    @Test
    public void testDeleteExamen_BodyOkResultTrue() throws Exception{
        Examen ex = new Examen(1L, "abc", 5.0, null, null);
        Optional<Examen> expectedFromDB = Optional.of(ex);
        when(examenRepository.findById(any(Long.class))).thenReturn(expectedFromDB);
        
        //Input
        String input = "1";
        
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected
        Boolean exp = true;
        
        //Test
        Boolean result = instance.deleteExamen(input);
        
        //Validation
        assertEquals(exp, result);
    }
    
    @Test
    public void testGetExamen_idOkExamenNotFound() throws Exception{
        when(examenRepository.findById(any(Long.class))).thenReturn(null);
        
        //Input
        String input = "1";
        
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected exception
        assertThrows(Exception.class, () -> {
            instance.getExamen(input);
        });
    }
    
    @Test
    public void testGetExmane_idOkResultOk() throws Exception{
        Examen ex = new Examen(1L, "abc", 5.0, null, null);
        Optional<Examen> expectedFromDB = Optional.of(ex);
        when(examenRepository.findById(any(Long.class))).thenReturn(expectedFromDB);
        
        //Input
        String input = "1";
        
        //Target
        ExamenService instance = new ExamenService(examenRepository, preguntaRepository, profesorRepository, examenMapper);
        
        //Expected
        ExamenDTO expected = new ExamenDTO(1L, "abc", 5.0, 5, 1L);
        
        //Test
        ExamenDTO result = instance.getExamen(input);
        
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getDescripcion(), result.getDescripcion());
        assertEquals(expected.getNota_maxima(), result.getNota_maxima());
    }
}
