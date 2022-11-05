package com.jsolutions.springcloud.msvc.courses.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;

}
