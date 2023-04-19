package com.jsolutions.springcloud.msvc.users.controllers;

import com.jsolutions.springcloud.msvc.users.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.jsolutions.springcloud.msvc.users.services.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    
    @GetMapping("/")
    public List<User> list(){
        return service.listAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> optionalUser =  service.findById(id);
        return optionalUser.map(user -> ResponseEntity.ok()
                .body(user)).orElseGet(() -> ResponseEntity.notFound()
                .build());
    }


    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<User>> getUsersByLastname(@PathVariable String lastname){
        List<User> users =  service.findByLastname(lastname);
        return ResponseEntity.ok()
                .body(users);
    }

    @GetMapping("/users-details")
    public ResponseEntity<List<User>> getUsersDetailsByCourse(@RequestParam List<Long> ids){
        List<User> users =  service.findAllByIds(ids);
        return ResponseEntity.ok()
                .body(users);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){

        if(result.hasErrors()){
            return validate(result);
        }

        if(!user.getEmail().isEmpty() && service.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("mensaje", "Ya existe usuario con ese email"));
        }

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result){

        if(result.hasErrors()){
            return validate(result);
        }
        Optional<User> optionalUser =  service.findById(id);
        if(optionalUser.isPresent()){
            User userDB = optionalUser.get();
            if(!user.getEmail().isEmpty() &&
               !user.getEmail().equalsIgnoreCase(userDB.getEmail()) &&
                service.findByEmail(user.getEmail()).isPresent()){
                return ResponseEntity
                        .badRequest()
                        .body(Collections.singletonMap("mensaje", "Ya existe usuario con ese email"));
            }
            userDB.setId(id);
            userDB.setNombre(user.getNombre());
            userDB.setApellido(user.getApellido());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.save(userDB));
        }else{
            return ResponseEntity.notFound()
            .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<User> optionalUser =  service.findById(id);
        if(optionalUser.isPresent()){
         service.delete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound()
            .build();
        }
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), "Error en el campo "+err.getField()+" : "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
