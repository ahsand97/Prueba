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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos");
        }
        else{
            List<ExamenDTO> resp = examenService.getExamenes(idProfesor);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> addExamen(@RequestBody ExamenDTO examenDTO){
        try{
            ExamenDTO resp = examenService.addExamen(examenDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }
    
    @DeleteMapping()
    public ResponseEntity<?> deleteExamen(@RequestParam("id") String id){
        try{
            examenService.deleteExamen(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/examen")
    public ResponseEntity<?> getExamen(@RequestParam("id") String idExamen){
        try{
            ExamenDTO resp = examenService.getExamen(idExamen);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
