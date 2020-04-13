/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Examen;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ExamenDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author ahsan
 */
@Mapper(componentModel = "spring")
public interface ExamenMapper {
    
    @Mapping(target = "profesor", ignore = true)
    @Mapping(target = "preguntas", ignore = true)
    Examen toExamen(ExamenDTO examenDTO);
    
    @InheritInverseConfiguration
    @Mapping(target = "profesor_id", ignore = true)
    @Mapping(target = "cantidad_preguntas", ignore = true)
    ExamenDTO fromExamen(Examen examen);
    
}