/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.dto;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Pregunta;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ahsan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamenDTO implements Serializable {
    
    private Long id;
    private String descripcion;
    private BigDecimal nota_maxima;
    private ProfesorDTO proferor;
    private List<PreguntaDTO> preguntas;
    
    public boolean addPregunta(PreguntaDTO preguntaDTO) {
        if(preguntas == null){
            preguntas = new ArrayList<>();
        }
        return preguntas.add(preguntaDTO);
    }
    
}
