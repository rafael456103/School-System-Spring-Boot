package com.project.demo.service;

import com.project.demo.dto.studentDTO.StudentUpdateDTO;
import com.project.demo.entity.Student;
import com.project.demo.dto.studentDTO.StudentLoginDTO;
import com.project.demo.dto.studentDTO.StudentRegisterDTO;
import com.project.demo.dto.studentDTO.StudentResponseDTO;

import java.util.List;
import java.util.Optional;


public interface StudentService {


    List<StudentResponseDTO> getStudents();
    Student getStudent(Long id);
    Student saveStudent(Student student);
    void deleteStudent(Long id);
    Optional<Student> findbyEmail(String email);

    Student updateStudent(StudentUpdateDTO studentUpdateDTO, Long id);
    StudentResponseDTO registerDTO(StudentRegisterDTO studentRegisterDTO);
    StudentResponseDTO loginDTO(StudentLoginDTO studentLoginDTO);
}
