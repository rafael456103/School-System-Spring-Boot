package com.project.demo.repository;

import com.project.demo.dto.studentDTO.StudentResponseDTO;
import com.project.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT new com.project.demo.dto.studentDTO.StudentResponseDTO(s.id, s.name, s.university, s.course, s.email) FROM Student s")
    List<StudentResponseDTO> getStudents();
    Optional<Student> findByEmail(String email);

}
