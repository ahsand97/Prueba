/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.dto;

import java.io.Serializable;
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
public class PreguntaDTO implements Serializable {
    
    private Long id;
    private Integer numero_pregunta;
    private String descripcion;
    private byte[] imagen;
    private Double valoracion;
    private String dtype;
    private Long examen_id;
    
}
