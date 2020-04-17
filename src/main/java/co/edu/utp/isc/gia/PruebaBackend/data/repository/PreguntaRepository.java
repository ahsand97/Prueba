/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.repository;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Pregunta;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.PreguntaDTOInterface;

/**
 *
 * @author ahsan
 * @param <T>
 */
@Repository
public interface PreguntaRepository<T extends Pregunta> extends CrudRepository<T, Long>{
    
    @Query(value="SELECT COUNT (*) FROM preguntas WHERE examen_id = :idExamen", nativeQuery=true)
    Integer findCantidadPreguntasByIdExamen(@Param("idExamen") Long idExamen);
    
    @Query(value="SELECT id,numero_pregunta,descripcion,imagen,valoracion,tipo_pregunta,examen_id FROM preguntas WHERE examen_id = :idExamen ORDER BY numero_pregunta", nativeQuery = true)
    List<PreguntaDTOInterface> findPreguntasByIdExamen(@Param("idExamen") Long idExamen);
    
    @Query(value="SELECT id FROM preguntas WHERE examen_id = :idExamen AND numero_pregunta = :numeroPregunta", nativeQuery=true)
    Long findIdPregunta(@Param("idExamen") String idExamen, @Param("numeroPregunta") String numeroPregunta);
    
    @Query(value="SELECT id, tipo_pregunta FROM preguntas WHERE examen_id = :idExamen AND numero_pregunta = :numeroPregunta", nativeQuery=true)
    Map<String,BigInteger> findIdAndTipoPregunta(@Param("idExamen") String idExamen, @Param("numeroPregunta") String numeroPregunta);
}
