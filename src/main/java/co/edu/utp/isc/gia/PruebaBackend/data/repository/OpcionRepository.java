/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.repository;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Opcion;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.OpcionDTOInterface;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ahsan
 */
@Repository
public interface OpcionRepository extends CrudRepository<Opcion, Long>{
    
    @Query(value="SELECT * FROM opciones WHERE pregunta_unica_respuesta_id = :idPregunta OR pregunta_multiples_respuestas_id = :idPregunta OR respuesta_multiple_id = :idPregunta ORDER BY letra", nativeQuery=true)
    List<OpcionDTOInterface> findOpcionesByIdPregunta(@Param("idPregunta") String idPregunta);
}
