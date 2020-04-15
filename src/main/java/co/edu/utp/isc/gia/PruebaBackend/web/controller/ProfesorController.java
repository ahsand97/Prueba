/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.ProfesorService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.ProfesorDTO;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ahsan
 */
@RestController
@CrossOrigin
@RequestMapping("profesores")
public class ProfesorController {
    
    private final ProfesorService profesorService;
    
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }
    
    @PostMapping()
    public ResponseEntity<?> validateProfesor(@RequestBody Map<String, String> profesor){
        if((profesor.get("email").isBlank() || profesor.get("email").isEmpty()) || (profesor.get("clave").isBlank() || profesor.get("clave").isEmpty())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos.");
        }
        else{
            try{
                ProfesorDTO resp = profesorService.validate(profesor);
                return ResponseEntity.status(HttpStatus.OK).body(resp);
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }
}
