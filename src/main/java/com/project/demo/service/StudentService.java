package com.project.demo.service;

import com.project.demo.entity.Student;
import com.project.demo.studentDto.StudentLoginDTO;
import com.project.demo.studentDto.StudentRegisterDTO;
import com.project.demo.studentDto.StudentResponseDTO;

import java.util.List;
import java.util.Optional;


public interface StudentService {


    List<Student> getStudents();
    Optional<Student> getStudent(Long id);
    Student saveStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudent(Long id);

    Optional<Student> findbyEmail(String email);
    StudentResponseDTO registerDTO(StudentRegisterDTO studentRegisterDTO);
    StudentResponseDTO loginDTO(StudentLoginDTO studentLoginDTO);
}
