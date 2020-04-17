/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.OpcionService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.OpcionDTO;
import java.util.List;
import java.util.Map;
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
@RequestMapping("opciones")
public class OpcionController {
    
    private final OpcionService opcionService;
    
    public OpcionController(OpcionService opcionService){
        this.opcionService = opcionService;
    }
    
    @PostMapping()
    public ResponseEntity<?> addOpcion(@RequestBody OpcionDTO opcionDTO){
        try{
            Boolean resp = opcionService.addOpcion(opcionDTO);
            if(resp == true){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/respuesta")
    public ResponseEntity<?> addRespuesta(@RequestBody OpcionDTO opcionDTO){
        try{
            Boolean resp = opcionService.addRespuesta(opcionDTO);
            if(resp == true){
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } 
    }
    
    @GetMapping()
    public ResponseEntity<?> getOpciones(@RequestParam(name = "id") String id){
        try{
            List<Map<String,String>> resp = opcionService.getOpciones(id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping("/respuesta")
    public ResponseEntity<?> getRespuesta(@RequestParam(name = "id") String id){
        try{
            OpcionDTO resp = opcionService.getRespuesta(id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
