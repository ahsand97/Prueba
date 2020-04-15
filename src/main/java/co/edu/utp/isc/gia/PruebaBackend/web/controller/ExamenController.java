/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.ExamenService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ExamenDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahsan
 */
@RestController
@CrossOrigin
@RequestMapping("examenes")
public class ExamenController {
    
    private final ExamenService examenService;
    
    public ExamenController(ExamenService examenService) {
        this.examenService = examenService;
    }
    
    @GetMapping()
    public ResponseEntity<?> getExamenes(@RequestParam("idProfesor") String idProfesor){
        if(idProfesor.isBlank() || idProfesor.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            List<ExamenDTO> resp = examenService.getExamenes(idProfesor);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
    }
}
