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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ahsan
 */
@RestController
@CrossOrigin
@RequestMapping("profesor")
public class ProfesorController {
    
    private final ProfesorService profesorService;
    
    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }
    
    @PostMapping()
    public ResponseEntity<?> validateProfesor(@RequestBody Map<String, String> profesor){
        if(profesor.get("email") == null || profesor.get("clave") == null){
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
    
    /*
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
        UserDTO respuesta = userService.findByUsername(username);
        if(respuesta == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ningún usuario con ese nombre de usuario.");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }*/
    
    //private final UserService userService;
    
    /*public UserController(UserService userService) {
        this.userService = userService;
    }
    
    
    
    
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO usuario){
        if(usuario.getEmail() == null || usuario.getNombre() == null || usuario.getPassword() == null || usuario.getUsuario() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan datos.");
        }
        else{
            UserDTO respuesta = userService.update(usuario);
            if(respuesta == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ningún usuario con ese nombre de usuario para actualizar datos.");
            }
            else{
                return ResponseEntity.status(HttpStatus.OK).body(respuesta);
            }
        }
    }
    
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
        int respuesta = userService.delete(username);
        if(respuesta == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ningún usuario con ese nombre de usuario para eliminar.");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        
    }
    
    
    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
        UserDTO respuesta = userService.findByUsername(username);
        if(respuesta == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ningún usuario con ese nombre de usuario.");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
    
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){
        UserDTO respuesta = userService.findById(id);
        if(respuesta == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe ningún usuario con ese id.");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(respuesta);
        }
    }
    
    
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }*/
}
