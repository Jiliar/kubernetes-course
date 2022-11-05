package com.jsolutions.springcloud.msvc.courses.services;

import java.util.List;
import java.util.Optional;

import com.jsolutions.springcloud.msvc.courses.models.entities.Course;

public interface CourseService {
    
    List<Course> lisAll();
    Optional<Course> findById(Long id);
    Course save(Course user);
    void delete(Long id);
    
}
