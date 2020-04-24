/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.service;

import co.edu.utp.isc.gia.PruebaBackend.data.entity.Estudiante;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.EstudianteDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author ahsan
 */
@Mapper(componentModel = "spring")
public interface EstudianteMapper {
    
    @Mapping(target = "nota", ignore = true)
    Estudiante toEstudiante(EstudianteDTO estudianteDTO);
    
    @InheritInverseConfiguration
    @Mapping(target = "examen_id", ignore = true)
    EstudianteDTO fromEstudiante(Estudiante estudiante);
}
