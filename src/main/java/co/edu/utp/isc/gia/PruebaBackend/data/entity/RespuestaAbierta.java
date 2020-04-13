/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.ToString;

/**
 *
 * @author ahsan
 */

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@Table(name = "respuestas_abiertas")
public class RespuestaAbierta extends Respuesta {

    @Column(nullable = false)
    private String respuesta;
    
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pregunta_id", nullable = false)
    private PreguntaRespuestaAbierta preguntaRespuestaAbierta;
}
