package com.jsolutions.springcloud.msvc.courses.services.impl;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jsolutions.springcloud.msvc.courses.models.entities.Course;
import com.jsolutions.springcloud.msvc.courses.models.entities.CourseUser;
import com.jsolutions.springcloud.msvc.courses.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsolutions.springcloud.msvc.courses.clients.UserClientRest;
import com.jsolutions.springcloud.msvc.courses.models.User;
import com.jsolutions.springcloud.msvc.courses.services.CourseUserService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    private UserClientRest client;

    @Autowired
    private CourseRepository repository;
    
    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long idCourse) {
        Optional<Course> courseDB = repository.findById(idCourse);
        if(courseDB.isPresent()){
            User userMsvc =  client.getUserById(user.getId());
            Course course = courseDB.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setIdUsuario(userMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<User> createAssignUser(User user, Long idCourse) {
        Optional<Course> courseDB = repository.findById(idCourse);
        if(courseDB.isPresent()){
            User newUserMsvc =  client.create(user);
            Course course = courseDB.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setIdUsuario(newUserMsvc.getId());
            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(newUserMsvc);
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<User> removeUserFromCourse(User user, Long idCourse) {
        Optional<Course> courseDB = repository.findById(idCourse);
        if(courseDB.isPresent()){
            User userMsvc =  client.getUserById(user.getId());
            Course course = courseDB.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setIdUsuario(userMsvc.getId());
            course.removeCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getUsersDetailsByCourse(Long idCourse) {
        Optional<Course> courseDB = repository.findById(idCourse);
        if(courseDB.isPresent()){
            Course course = courseDB.get();
            if(!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers()
                                     .stream()
                                     .map(CourseUser::getIdUsuario)
                                     .collect(Collectors.toList());
                
                List<User> users = client.getUsersDetailsByCourse(ids);
                course.setUsers(users);
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteUserInCourse(Long idUser) {
        repository.deleteUserInCourse(idUser);
    }


}
