/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Pregunta;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.PreguntaDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author ahsan
 */
@Mapper(componentModel = "spring")
public interface PreguntaMapper {
    
    @InheritConfiguration
    @Mapping(target = "examen", ignore = true)
    Pregunta toPregunta(PreguntaDTO preguntaDTO);
    
    @InheritInverseConfiguration
    @Mapping(target = "examen_id", ignore = true)
    PreguntaDTO fromPregunta(Pregunta pregunta);
    
}
