package com.project.demo.dto.studentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentUpdateDTO {
    private String name;
    private String university;
    private String course;
    private String email;
}
