/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.repository;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Examen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ahsan
 */
@Repository
public interface ExamenRepository extends CrudRepository<Examen, Long>{
    
    @Query(value="SELECT * FROM examenes WHERE profesor_id = :idProfesor", nativeQuery=true)
    Iterable<Examen> findAllExamenesByIdProfesor(@Param("idProfesor") String idProfesor);
}
