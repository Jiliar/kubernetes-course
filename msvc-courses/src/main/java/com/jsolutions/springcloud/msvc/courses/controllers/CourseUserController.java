package com.jsolutions.springcloud.msvc.courses.controllers;

import com.jsolutions.springcloud.msvc.courses.models.User;
import com.jsolutions.springcloud.msvc.courses.models.entities.Course;
import com.jsolutions.springcloud.msvc.courses.services.CourseService;
import com.jsolutions.springcloud.msvc.courses.services.CourseUserService;
import com.jsolutions.springcloud.msvc.courses.utils.Utils;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseUserController {

    @Autowired
    private CourseUserService service;

    @Autowired
    private CourseService courseService;

    @GetMapping("/user-details-by-course/{id}")
    public ResponseEntity<Course> getCourseUserDetailsById(@PathVariable Long id){
        Optional<Course> optionalCourse =  service.getUsersDetailsByCourse(id);
        return optionalCourse.map(course -> ResponseEntity.ok()
                .body(course)).orElseGet(() -> ResponseEntity.notFound()
                .build());
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@PathVariable Long courseId, @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return Utils.validate(result);
        }
        Optional<User> userAssigned;
        try {
            userAssigned = service.assignUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo asignar el usuario "+e.getMessage()));
        }
        if(userAssigned.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userAssigned.get());
        }
            return ResponseEntity
                    .notFound()
                    .build();
    }



    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@PathVariable Long courseId, @RequestBody User user, BindingResult result) {
        Optional<User> o;
        try {
            o = service.createAssignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario " +
                            "o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/remove-user/{courseId}")
    public ResponseEntity<?> removeUser(@PathVariable Long courseId, @RequestBody User user, BindingResult result) {
        Optional<User> o;
        try {
            o = service.removeUserFromCourse(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{idUser}")
    public ResponseEntity<?> deleteUserInCourseById(@PathVariable Long idUser){
        service.deleteUserInCourse(idUser);
        return ResponseEntity.noContent().build();
    }

}
