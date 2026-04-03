package com.project.demo.controller;

import com.project.demo.entity.Student;
import com.project.demo.service.StudentService;
import com.project.demo.dto.studentDTO.StudentLoginDTO;
import com.project.demo.dto.studentDTO.StudentRegisterDTO;
import com.project.demo.dto.studentDTO.StudentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/SchoolSystem")
public class StudentController {

    //Inyeccion de Dependencias por Constructor
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudent() {
        try {
            return ResponseEntity.ok(studentService.getStudents());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id){
        try {
            return ResponseEntity.ok(studentService.getStudent(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estudiante no encontrado: " + e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/save")
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(studentService.saveStudent(student));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PutMapping("/update_Student")
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(studentService.updateStudent(student));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginStudent(@Valid @RequestBody StudentLoginDTO studentLoginDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(studentService.loginDTO(studentLoginDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegisterDTO studentRegisterDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(studentService.registerDTO(studentRegisterDTO));
        } catch (RuntimeException e) {
            // Esto te diría "The student already exist" o errores de validación
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Esto te mostrará el error de SQL o NullPointer en Postman
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}