package com.project.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Table(name = "students")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotBlank(message = "El nombre no es valido")
    @Size(min = 2, max = 60)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Debe contener una contrasena de al menos 5 a 14 caracteres validos.")
    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 60)
    private String password;

    @NotBlank(message = "Debes introducir tu universidad.")
    @Size(max = 90)
    @Column(name = "university", nullable = false, length = 90)
    private String university;

    @Size(min = 7, max = 7)
    @NotBlank(message = "Por favor introduce un curso.")
    @Column(nullable = false, name = "course", length = 7)
    private String course;

    @Email
    @NotBlank
    @Size(min = 12, max = 100)
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
}
