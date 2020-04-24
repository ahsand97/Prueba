/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.RespuestaService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.EstudianteDTO;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.RespuestaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("respuestas")
public class RespuestaController {
    
    private final RespuestaService respuestaService;
    
    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }
    
    @PostMapping("/estudiante")
    public ResponseEntity<?> addEstudiante(@RequestBody EstudianteDTO estudianteDTO){
        try{
            EstudianteDTO resp = respuestaService.addEstudiante(estudianteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> addRespuesta(@RequestBody RespuestaDTO respuestaDTO){
        try{
            RespuestaDTO resp = respuestaService.addRespuesta(respuestaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/estudiante")
    public ResponseEntity<?> getNotaEstudiante(@RequestParam(name = "id") String id){
        try{
            Double resp = respuestaService.getNota(id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }
}
