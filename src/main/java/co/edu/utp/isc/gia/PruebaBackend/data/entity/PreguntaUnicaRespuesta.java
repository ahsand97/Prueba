/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author ahsan
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@Table(name = "preguntas_unica_respuesta")
public class PreguntaUnicaRespuesta extends Pregunta {
    
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "preguntaUnicaRespuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pregunta_unica_respuesta_id")
    private List<Opcion> opciones;
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "opcion_respuesta_id", nullable = false)
    private Opcion opcionRespuesta;
    
    public boolean addOpcion(Opcion opcion) {
        if(opciones == null){
            opciones = new ArrayList<>();
        }
        return opciones.add(opcion);
    }
    
}
