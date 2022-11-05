package com.jsolutions.springcloud.msvc.courses.repositories;

import com.jsolutions.springcloud.msvc.courses.models.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

    @Modifying
    @Query("DELETE FROM CourseUser cu WHERE cu.idUsuario = ?1")
    void deleteUserInCourse(Long idUser);

}
