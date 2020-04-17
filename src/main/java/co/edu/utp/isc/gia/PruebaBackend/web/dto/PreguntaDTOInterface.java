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

public interface PreguntaDTOInterface {
    Long getid();
    Integer getnumero_pregunta();
    String getdescripcion();
    byte[] getimagen();
    Double getvaloracion();
    String gettipo_pregunta();
    Long getexamen_id();
}
