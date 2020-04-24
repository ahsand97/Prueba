/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.ExamenService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ExamenDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 *
 * @author ahsan
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ExamenControllerTest {
    
    public ExamenControllerTest() {
    }
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ExamenService examenService;

    @Test
    public void testGetExamenesidProfesorOkExpOk() throws Exception {
        ExamenDTO examen = new ExamenDTO(1L, "Examen 1", 5.0, 5, 1L);
        ExamenDTO examen2 = new ExamenDTO(2L, "Examen 2", 5.0, 5, 1L);
        ExamenDTO examen3 = new ExamenDTO(3L, "Examen 3", 5.0, 5, 1L);
        ExamenDTO examen4 = new ExamenDTO(4L, "Examen 4", 5.0, 5, 1L);
        List<ExamenDTO> expectedFromService = new ArrayList<>();
        expectedFromService.addAll(Arrays.asList(examen, examen2, examen3, examen4));
        
        given(examenService.getExamenes(any(String.class))).willReturn(expectedFromService);
       
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/examenes").param("idProfesor", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        List<ExamenDTO> resultList = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<List<ExamenDTO>>() {});
        
        Iterator<ExamenDTO> it1 = expectedFromService.iterator();
        Iterator<ExamenDTO> it2 = resultList.iterator();

        //Validation
        while(it1.hasNext() && it2.hasNext()){
            ExamenDTO a = it1.next();
            ExamenDTO b = it2.next();
            assertEquals(a.getId(), b.getId());
            assertEquals(a.getCantidad_preguntas(), b.getCantidad_preguntas());
            assertEquals(a.getDescripcion(), b.getDescripcion());
            assertEquals(a.getNota_maxima(), b.getNota_maxima());
            assertEquals(a.getProfesor_id(), b.getProfesor_id());
        }
    }
    
    @Test
    public void testGetExamenesidProfesorNullExpBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/examenes").param("idProfesor", ""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
    @Test
    public void testAddExamenBodyOkExpOk() throws Exception {
        ExamenDTO expectedFromService = new ExamenDTO(1L, "Exámen prueba 1", 5.0, null, 1L);
        given(examenService.addExamen(any(ExamenDTO.class))).willReturn(expectedFromService);
        
        ExamenDTO inputToAPI = new ExamenDTO(null, "Exámen Prueba 1", 5.0, 10, 1L);
        
       
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/examenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputToAPI)))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        
        ExamenDTO resultFromController = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<ExamenDTO>() {});
        
        assertEquals(expectedFromService.getId(), resultFromController.getId());
        assertEquals(expectedFromService.getDescripcion(), resultFromController.getDescripcion());
        assertEquals(expectedFromService.getNota_maxima(), resultFromController.getNota_maxima());
        assertEquals(expectedFromService.getProfesor_id(), resultFromController.getProfesor_id());
    }
    
    @Test
    public void testAddExamenBadBodyExpException() throws Exception {
        given(examenService.addExamen(any(ExamenDTO.class))).willThrow(new Exception());
        
        ExamenDTO inputToAPI = new ExamenDTO(null, "Exámen Prueba 1", 5.0, 10, 1L);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/examenes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputToAPI)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
}
