/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Profesor;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ProfesorDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 *
 * @author ahsan
 */
@Mapper(componentModel = "spring")
public interface ProfesorMapper {
    
    Profesor toProfesor(ProfesorDTO profesorDTO);
    
    @InheritInverseConfiguration
    //@Mapping(target = "password", ignore = true)
    ProfesorDTO fromProfesor(Profesor profesor);
    
}
