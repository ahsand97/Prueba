/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.PreguntaService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.PreguntaDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ahsan
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PreguntaControllerTest {
    
    public PreguntaControllerTest() {
    }
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private PreguntaService preguntaService;

    @Test
    public void testAddPreguntaInputOkExpectedOk() throws Exception {
        PreguntaDTO expectedFromService = new PreguntaDTO(1L, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta con respuesta abierta", 22L);
        given(preguntaService.addPregunta(any(PreguntaDTO.class))).willReturn(expectedFromService);
        
        PreguntaDTO inputToAPI = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta con respuesta abierta", 22L);
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/preguntas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputToAPI)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        
        PreguntaDTO resultFromController = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<PreguntaDTO>() {});
        
        assertEquals(expectedFromService.getId(), resultFromController.getId());
        assertEquals(expectedFromService.getDescripcion(), resultFromController.getDescripcion());
        assertEquals(expectedFromService.getNumero_pregunta(), resultFromController.getNumero_pregunta());
        assertEquals(expectedFromService.getDtype(), resultFromController.getDtype());
        assertEquals(expectedFromService.getExamen_id(), resultFromController.getExamen_id());
        assertEquals(expectedFromService.getValoracion(), resultFromController.getValoracion());
    
    }
    
    @Test
    public void testAddPreguntaInputOkNoSeEncuentraLaPreguntaExpectedException() throws Exception {
        given(preguntaService.addPregunta(any(PreguntaDTO.class))).willThrow(new Exception());
        
        PreguntaDTO inputToAPI = new PreguntaDTO(null, 1, "¿Cuál es la capital de Colombia?", null, 1.5, "Pregunta con respuesta abierta", 22L);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/preguntas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputToAPI)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
    @Test
    public void testAddImageBodyOkResultOk() throws Exception {
   
        doNothing().when(preguntaService).addImagenToPregunta(any(String.class), any(String.class), any(MultipartFile.class));
        
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        
        mockMvc.perform(MockMvcRequestBuilders.multipart("/preguntas/uploadImage")
                .file("imagen", firstFile.getBytes())
                .param("id", "1")
                .param("idP", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }
    
    @Test
    public void testAddImageBodyOkExpectedException() throws Exception {
   
        doThrow(new Exception()).when(preguntaService).addImagenToPregunta(any(String.class), any(String.class), any(MultipartFile.class));
        
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        
        mockMvc.perform(MockMvcRequestBuilders.multipart("/preguntas/uploadImage")
                .file("imagen", firstFile.getBytes())
                .param("id", "1")
                .param("idP", "1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
}
