package com.project.demo.service;


import com.project.demo.dto.studentDTO.StudentUpdateDTO;
import com.project.demo.entity.Student;
import com.project.demo.repository.StudentRepository;
import com.project.demo.dto.studentDTO.StudentLoginDTO;
import com.project.demo.dto.studentDTO.StudentRegisterDTO;
import com.project.demo.dto.studentDTO.StudentResponseDTO;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    //Inyeccion de dependencias por constructor
    final StudentRepository studentRepository;
    final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<StudentResponseDTO> getStudents() {
        return studentRepository.getStudents();
    }

    @Override
    public Student getStudent(Long id) {
       return studentRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Student doesn't founded " + id));
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(StudentUpdateDTO studentUpdateDTO, Long id) {

        Student finded = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student doesn't founded"));

        finded.setName(studentUpdateDTO.getName());
        finded.setUniversity(studentUpdateDTO.getUniversity());
        finded.setCourse(studentUpdateDTO.getCourse());
        finded.setEmail(studentUpdateDTO.getEmail());

        return studentRepository.save(finded);
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
        //Verificar si existe el estudiante
        if(studentRepository.findByEmail(studentRegisterDTO.getEmail()).isPresent()){
            throw new RuntimeException("The student already exist");
        }
        //Guardar al estudiante
        Student saved = studentRepository.save(Student.builder()
                .name(studentRegisterDTO.getName())
                .university(studentRegisterDTO.getUniversity())
                .email(studentRegisterDTO.getEmail())
                .course(studentRegisterDTO.getCourse())
                .password(passwordEncoder.encode(
                        studentRegisterDTO.getPassword())).build());

        //Retornar la entidad solo con los datos indispensables
        return StudentResponseDTO.builder()
                .name(saved.getName())
                .email(saved.getEmail())
                .university(saved.getUniversity())
                .course(saved.getCourse())
                .id(saved.getId()).build();
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
