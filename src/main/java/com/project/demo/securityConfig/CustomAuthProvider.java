package com.project.demo.securityConfig;

import com.project.demo.entity.Student;
import com.project.demo.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        Optional<Student> studentOptional = studentRepository
                .findByEmail(email);
        if(studentOptional.isEmpty()){
            log.warn("El email no esta asociado a ningun estudiante ", email);
            throw new BadCredentialsException("Estudiante no encontrado");
        }

        Student student = studentOptional.get();
        if (passwordEncoder.matches(rawPassword, student.getPassword())){
            log.warn("La contrasena no coincide",rawPassword);
            throw new BadCredentialsException("Contrasena incorrecta");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        log.info("Entidad Student autorizada con exito.");

        return UsernamePasswordAuthenticationToken.authenticated(
                student.getEmail(),
                null,
                authorities
        );
    }
    @Override
    public boolean supports(Class<?> authorize){
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authorize);
    }
}
