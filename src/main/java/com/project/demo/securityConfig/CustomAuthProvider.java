package com.project.demo.securityConfig;
import com.project.demo.entity.Student;
import com.project.demo.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
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

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthProvider(StudentRepository studentRepository, PasswordEncoder passwordEncoder){
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication){
        //Obtener datos
        String email = authentication.getName();
        String passwordRaw = authentication.getCredentials().toString();

        //Verificar email
        Optional<Student> student = studentRepository.findByEmail(email);
        if(student.isEmpty()){
            log.error("Bad credentials");
            throw new BadCredentialsException("Bad credentials");
        }

        //Verificar Password
        Student student1 = student.get();
        if (!passwordEncoder.matches(passwordRaw,student1.getPassword() )){
            log.error("Bad credentials");
            throw new BadCredentialsException("Bad credentials");
        }

        //Crear lista de roles autorizados
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        //retornar authenticacion
        return UsernamePasswordAuthenticationToken.authenticated (
                email,
                null,
                authorities);
    }

    @Override
    public boolean supports(Class<?> aClass){
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(aClass);
    }
}