package com.jsolutions.springcloud.msvc.users.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo nombre no puede ser vacio")
    @Column(name="name", unique = true)
    private String nombre;

    @NotBlank(message = "Campo apellido no puede ser vacio")
    @Column(name="lastname")
    private String apellido;

    @NotBlank(message = "Campo email no puede ser vacio")
    @Email(message = "El campo email no tiene un formato valido")
    @Column(name="email", unique = true)
    private String email;

    @NotEmpty(message = "Campo password no puede ser vacio")
    @Min(8)
    @Column(name="password")
    private String password;
    
}
