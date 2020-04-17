/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Profesor;
import co.edu.utp.isc.gia.PruebaBackend.data.repository.ProfesorRepository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ProfesorDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class ProfesorServiceTest {
    
    public ProfesorServiceTest() {
    }
    
    @Mock
    private ProfesorRepository profesorRepository;
    
    @Autowired
    private ProfesorMapper profesorMapper;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidate_emailOkResultOk() throws Exception {
        Profesor expectedFromDB = new Profesor(1L, "Cesar Augusto Díaz", "black@utp.edu.co", "1234", new ArrayList<>());
        when(profesorRepository.findByEmail(any(String.class))).thenReturn(expectedFromDB);
        
        //Input
        Map<String, String> input = new HashMap<>();
        input.put("email", "black@utp.edu.co");
        input.put("clave", "1234");
       
        //Target
        ProfesorService instance = new ProfesorService(profesorRepository, profesorMapper);
        
        //Expected
        ProfesorDTO expResult = new ProfesorDTO(1L, "Cesar Augusto Díaz", "black@utp.edu.co");
        
        //Test
        ProfesorDTO result = instance.validate(input);
        
        //Validation
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getEmail(), result.getEmail());
        assertEquals(expResult.getNombre(), result.getNombre());
    }
    
    @Test
    public void testValidate_emailOkClaveMalResultException () throws Exception {
        Profesor expectedFromDB = new Profesor(1L, "Cesar Augusto Díaz", "black@utp.edu.co", "1234", new ArrayList<>());
        when(profesorRepository.findByEmail(any(String.class))).thenReturn(expectedFromDB);
        
        //Input
        Map<String, String> input = new HashMap<>();
        input.put("email", "black@utp.edu.co");
        input.put("clave", "456");
       
        //Target
        ProfesorService instance = new ProfesorService(profesorRepository, profesorMapper);
        
        //Expected
        assertThrows(Exception.class, () -> {
            instance.validate(input);
        });
    }
    
    @Test
    public void testValidate_emailOkResultExceptionNoEncontradoEnSistema() throws Exception {
        when(profesorRepository.findByEmail(any(String.class))).thenReturn(null);
        
        //Input
        Map<String, String> input = new HashMap<>();
        input.put("email", "black@utp.edu.co");
        input.put("clave", "1234");
       
        //Target
        ProfesorService instance = new ProfesorService(profesorRepository, profesorMapper);
        
        //Expected
        assertThrows(Exception.class, () -> {
            instance.validate(input);
        });
    }
    
    @Test
    public void testValidate_emailNullResultException(){
        //Input
        Map<String, String> input = null;
       
        //Target
        ProfesorService instance = new ProfesorService(profesorRepository, profesorMapper);
        
        //Expected exception
        //Test
        //Validation
        assertThrows(Exception.class, () -> {
            ProfesorDTO result = instance.validate(input);
        });
    }
}
