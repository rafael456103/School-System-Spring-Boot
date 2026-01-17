package com.project.demo.securityConfig;

import com.project.demo.entity.Student;
import com.project.demo.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication){
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        if(studentOptional.isEmpty()){
            log.warn("this student doesn't founded");
            throw new BadCredentialsException("Student doesn't founded");
        }

        Student student = studentOptional.get();
        if(!passwordEncoder.matches(rawPassword, student.getPassword())){
            log.warn("Student password is incorrect");
            throw new BadCredentialsException("This password is incorrect");
        }

        //Se crea una Lista Tipo GrantedAuthority para guardar las autoridades
        List<GrantedAuthority> authorities = new ArrayList<>();
        //Se agrega una Autoirzacion simple e la lista con el role de estudiante
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        return UsernamePasswordAuthenticationToken
                .authenticated(email,null,authorities);
    }
    @Override
    public boolean supports(Class<?> aClass){
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(aClass);
    }
}