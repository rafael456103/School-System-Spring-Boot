package com.project.demo.dto.studentDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String university;
    private String course;
    private String email;
}
