package com.project.demo.studentDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterDTO {

    private String name;
    private String password;
    private String university;
    private String course;
    private String email;
}
