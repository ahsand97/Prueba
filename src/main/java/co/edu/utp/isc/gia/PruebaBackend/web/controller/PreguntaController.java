/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.utp.isc.gia.PruebaBackend.web.controller;

import co.edu.utp.isc.gia.PruebaBackend.service.PreguntaService;
import co.edu.utp.isc.gia.PruebaBackend.web.dto.PreguntaDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ahsan
 */
@RestController
@CrossOrigin
@RequestMapping("preguntas")
public class PreguntaController {
    
    private final PreguntaService preguntaService;
    
    public PreguntaController(PreguntaService preguntaService){
        this.preguntaService = preguntaService;
    }
    
    
    @PostMapping()
    public ResponseEntity<?> addPregunta(@RequestBody PreguntaDTO preguntaDTO){
        try{
            PreguntaDTO resp = preguntaService.addPregunta(preguntaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/uploadImage")
    public ResponseEntity<?> addImage(@RequestParam(name = "id") String Id, @RequestParam(name = "idP") String numeroPregunta, @RequestParam("imagen") MultipartFile imagen){
        try{
            preguntaService.addImagenToPregunta(Id, numeroPregunta, imagen);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping()
    public ResponseEntity<?> getPreguntas(@RequestParam(name = "id") String id){
        try{
            List<PreguntaDTO> resp = preguntaService.getPreguntas(id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
