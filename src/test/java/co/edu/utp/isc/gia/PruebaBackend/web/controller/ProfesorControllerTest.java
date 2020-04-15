/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.ProfesorService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ProfesorDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.json.JSONObject;
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
public class ProfesorControllerTest {
    
    public ProfesorControllerTest() {
    }
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ProfesorService profesorService;

    @Test
    public void testValidateBodyOkExpOk() throws Exception {
        ProfesorDTO expectedFromService = new ProfesorDTO(1L, "Cesar Augusto Díaz", "black@utp.edu.co");
        given(profesorService.validate(any(Map.class))).willReturn(expectedFromService);
        
        JSONObject jsonToController = new JSONObject();
        jsonToController.put("email", "black@utp.edu.co");
        jsonToController.put("clave", "1234");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/profesores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToController.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        ProfesorDTO resultFromController = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<ProfesorDTO>() {});
        
        assertEquals(expectedFromService.getId(), resultFromController.getId());
        assertEquals(expectedFromService.getNombre(), resultFromController.getNombre());
        assertEquals(expectedFromService.getEmail(), resultFromController.getEmail());
    }
    
    @Test
    public void testValidateBodyEmptyExpBadRequest() throws Exception {
        JSONObject jsonToController = new JSONObject();
        jsonToController.put("email", "");
        jsonToController.put("clave", "");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/profesores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonToController.toString()))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
    @Test
    public void testValidateBodyOkBadPasswordExpBadRequest() throws Exception {
        given(profesorService.validate(any(Map.class))).willThrow(new Exception("Contraseña incorrecta"));
        
        JSONObject jsonToController = new JSONObject();
        jsonToController.put("email", "black@utp.edu.co");
        jsonToController.put("clave", "123456");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/profesores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonToController.toString()))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
}
