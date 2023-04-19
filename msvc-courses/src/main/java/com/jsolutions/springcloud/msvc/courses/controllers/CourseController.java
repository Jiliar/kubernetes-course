package com.jsolutions.springcloud.msvc.courses.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jsolutions.springcloud.msvc.courses.clients.UserClientRest;
import com.jsolutions.springcloud.msvc.courses.models.User;
import com.jsolutions.springcloud.msvc.courses.models.entities.Course;
import com.jsolutions.springcloud.msvc.courses.services.CourseService;
import com.jsolutions.springcloud.msvc.courses.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @Autowired
    private UserClientRest client;
    
    @GetMapping("/")
    public List<Course> list(){
         return service.listAll()
                 .stream()
                .map(courseUser->{
                    List<User> users = new ArrayList<>();
                    courseUser.getCourseUsers()
                            .forEach(idUser->{
                                User user = client.getUserById(idUser.getIdUsuario());
                                users.add(user);
                            });
                    courseUser.setUsers(users.stream()
                            .sorted(Comparator.comparing(User::getApellido))
                            .collect(Collectors.toList()));
                    return courseUser;
                }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
        Optional<Course> optionalCourse =  service.findById(id);
        if(optionalCourse.isPresent()){
            return ResponseEntity.ok()
            .body(optionalCourse.get());
        }else{
            return ResponseEntity.notFound()
            .build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return Utils.validate(result);
        }
        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return Utils.validate(result);
        }

        Optional<Course> optionalCourse =  service.findById(id);
        if(optionalCourse.isPresent()){
            Course CourseDB = optionalCourse.get();
            CourseDB.setId(id);
            CourseDB.setNombre(course.getNombre());
            CourseDB.setEstado(course.getEstado());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service.save(CourseDB));
        }else{
            return ResponseEntity.notFound()
            .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Course> optionalCourse =  service.findById(id);
        if(optionalCourse.isPresent()){
            service.delete(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound()
            .build();
        }
    }



}
