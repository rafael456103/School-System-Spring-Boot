package com.project.demo.dto.studentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String university;
    private String course;
    private String email;
}
