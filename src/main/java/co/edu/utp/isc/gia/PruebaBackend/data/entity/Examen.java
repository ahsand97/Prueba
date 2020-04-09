/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.entity;

/**
 *
 * @author ahsan
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="examenes")
public class Examen implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private BigDecimal nota_maxima;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;
    
    public boolean addPregunta(Pregunta pregunta) {
        if(preguntas == null){
            preguntas = new ArrayList<>();
        }
        return preguntas.add(pregunta);
    }
}