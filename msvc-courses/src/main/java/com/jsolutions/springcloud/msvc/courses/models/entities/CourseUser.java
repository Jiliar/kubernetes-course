package com.jsolutions.springcloud.msvc.courses.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "course_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private Long idUsuario;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CourseUser)) {
            return false;
        }
        CourseUser o = (CourseUser) obj;
        return this.idUsuario != null && this.idUsuario.equals(o.idUsuario);
    }

}
