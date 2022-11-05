package com.jsolutions.springcloud.msvc.courses.services;

import java.util.Optional;

import com.jsolutions.springcloud.msvc.courses.models.User;
import com.jsolutions.springcloud.msvc.courses.models.entities.Course;

public interface CourseUserService {

    Optional<User> assignUser(User user, Long idCourse);
    Optional<User> createAssignUser(User user, Long idCourse);
    Optional<User> removeUserFromCourse(User user, Long idCourse);
    Optional<Course> getUsersDetailsByCourse(Long id);
    void deleteUserInCourse(Long idUser);
}
