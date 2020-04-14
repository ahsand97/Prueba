/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author ahsan
 */
@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@DiscriminatorValue("multiples_respuestas")
@Table(name = "preguntas_multiples_respuestas")
public class PreguntaMultiplesRespuestas extends Pregunta {
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pregunta_multiples_respuestas_id")
    private List<Opcion> opciones;
    
    public boolean addOpcion(Opcion opcion) {
        if(opciones == null){
            opciones = new ArrayList<>();
        }
        return opciones.add(opcion);
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "respuesta_multiple_id")
    private List<Opcion> opcionesRespuesta;
    
    public boolean addRespuesta(Opcion opcion) {
        if(opcionesRespuesta == null){
            opcionesRespuesta = new ArrayList<>();
        }
        return opcionesRespuesta.add(opcion);
    }
}
