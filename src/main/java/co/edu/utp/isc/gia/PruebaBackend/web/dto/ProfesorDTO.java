/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.dto;

import java.io.Serializable;
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
public class ProfesorDTO implements Serializable{
    
    private Long id;
    private String nombre;
    private String email;
    private String clave;
    
    private List<ExamenDTO> examenes;
    
    public boolean addExamen(ExamenDTO examenDTO) {
        if(examenes == null){
            examenes = new ArrayList<>();
        }
        return examenes.add(examenDTO);
    }
}
