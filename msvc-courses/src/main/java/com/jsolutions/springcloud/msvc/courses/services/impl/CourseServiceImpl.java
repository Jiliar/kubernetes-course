package com.jsolutions.springcloud.msvc.courses.services.impl;

import java.util.List;
import java.util.Optional;

import com.jsolutions.springcloud.msvc.courses.models.entities.Course;
import com.jsolutions.springcloud.msvc.courses.repositories.CourseRepository;
import com.jsolutions.springcloud.msvc.courses.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> lisAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course Course) {
        return repository.save(Course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}