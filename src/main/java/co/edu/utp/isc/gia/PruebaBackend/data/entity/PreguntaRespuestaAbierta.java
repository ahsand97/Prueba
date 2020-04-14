/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author ahsan
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@DiscriminatorValue("respuesta_abierta")
@Table(name = "preguntas_respuesta_abierta")
public class PreguntaRespuestaAbierta extends Pregunta {
}
