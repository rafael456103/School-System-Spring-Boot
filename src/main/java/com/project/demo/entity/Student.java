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

    @NotBlank(message = "Name is not valid")
    @Size(min = 2, max = 60)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Password must contain at least 5 to 60 valid characters")
    @Size(min = 5, max = 60)
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "University is required")
    @Size(max = 90)
    @Column(name = "university", nullable = false, length = 90)
    private String university;

    @NotBlank(message = "Course is required")
    @Size(min = 7, max = 7)
    @Column(nullable = false, name = "course", length = 7)
    private String course;

    @Email
    @NotBlank(message = "Email is required")
    @Size(min = 12, max = 100)
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
}
