/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.data.repository;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Profesor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ahsan
 */
@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, Long>{
    
    @Query(value="SELECT * FROM PROFESORES WHERE email = :email", nativeQuery=true)
    Profesor findByEmail(@Param("email") String email);
}
