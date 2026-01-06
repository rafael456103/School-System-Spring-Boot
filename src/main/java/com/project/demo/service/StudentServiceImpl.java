package com.project.demo.service;


import com.project.demo.entity.Student;
import com.project.demo.repository.StudentRepository;
import com.project.demo.studentDto.StudentLoginDTO;
import com.project.demo.studentDto.StudentRegisterDTO;
import com.project.demo.studentDto.StudentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudent(Long id) {
       return studentRepository.findById(id);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findbyEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    @Override
    public StudentResponseDTO registerDTO(StudentRegisterDTO studentRegisterDTO){
        if (studentRepository.findByEmail(studentRegisterDTO.getEmail()).isPresent()){
            throw new RuntimeException("El email ingresado ya existe");
        }

        Student student = Student.builder()
                .name(studentRegisterDTO.getName())
                .password(passwordEncoder.encode(studentRegisterDTO.getPassword()))
                .university(studentRegisterDTO.getUniversity())
                .course(studentRegisterDTO.getCourse())
                .email(studentRegisterDTO.getEmail())
                .build();

        Student saved = studentRepository.save(student);

        return StudentResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .university(saved.getUniversity())
                .course(saved.getCourse())
                .email(saved.getEmail())
                .build();
    }

    @Override
    public StudentResponseDTO loginDTO(StudentLoginDTO studentLoginDTO){
        //verificar y crear objeto Estudiante del email proporcionado
        Student findedByEmail = studentRepository.
                findByEmail(studentLoginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("El email no coincide con ningun estudiante"));

        //verificar password
        if (!passwordEncoder
                .matches(studentLoginDTO.getPassword(),
                        findedByEmail.getPassword())){
            throw new RuntimeException("La contrasena no coincide");
        }
        return StudentResponseDTO.builder()
                .id(findedByEmail.getId())
                .name(findedByEmail.getName())
                .university(findedByEmail.getUniversity())
                .course(findedByEmail.getCourse())
                .email(findedByEmail.getEmail())
                .build();
    }
}
