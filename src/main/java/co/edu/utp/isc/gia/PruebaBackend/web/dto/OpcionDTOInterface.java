/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.dto;

/**
 *
 * @author ahsan
 */
public interface OpcionDTOInterface {
    Long getId();
    String getLetra();
    String getDescripcion();
    Long getpregunta_unica_respuesta_id();
    Long getpregunta_multiples_respuestas_id();
    Long getrespuesta_multiple_id();
    
    void setID(Long id);
    void setLetra(String letra);
    void setDescripcion(String descripcion);
    void setpregunta_unica_respuesta_id(Long pregunta_unica_respuesta_id);
    void setpregunta_multiples_respuestas_id(Long pregunta_multiples_respuestas_id);
    void setrespuesta_multiple_id(Long respuesta_multiple_id);
}
