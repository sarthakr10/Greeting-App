package com.exampe.Greeting.service;

import com.exampe.Greeting.dto.AuthUserDTO;
import com.exampe.Greeting.dto.LoginDTO;
import com.exampe.Greeting.model.AuthUser;
import com.exampe.Greeting.repository.AuthUserRepository;
import com.exampe.Greeting.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthUserRepository authUserRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthUserRepository authUserRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(AuthUserDTO authUserDTO) {
        if (authUserRepository.findByEmail(authUserDTO.getEmail()).isPresent()) {
            return "Email is already in use.";
        }
        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName());
        user.setEmail(authUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword())); // Encode password
        authUserRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(loginDTO.getEmail());
        if (userOptional.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), userOptional.get().getPassword())) {
            return "Invalid email or password!";
        }
        return jwtUtil.generateToken(userOptional.get().getEmail());
    }
}
