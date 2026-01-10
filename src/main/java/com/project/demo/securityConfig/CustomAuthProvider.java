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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CustomAuthProvider implements AuthenticationProvider {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    StudentRepository studentRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String rawpassword = authentication.getCredentials().toString();

        //find email student
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        if(studentOptional.isEmpty()){
            log.warn("Email student dont founded");
            new BadCredentialsException("Email student dont founded");
        }

        Student student = studentOptional.get();
        if(passwordEncoder.matches(rawpassword, student.getPassword())){
            log.warn("Password does not match");
            new BadCredentialsException("Password does not match");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        return UsernamePasswordAuthenticationToken.authenticated(email, null,authorities);
    }
    @Override
    public boolean supports(Class<?> authenticacion){
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticacion);
    }
}
