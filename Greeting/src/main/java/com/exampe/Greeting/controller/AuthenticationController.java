package com.exampe.Greeting.controller;

import com.exampe.Greeting.dto.AuthUserDTO;
import com.exampe.Greeting.dto.LoginDTO;
import com.exampe.Greeting.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AuthUserDTO authUserDTO) {
        LOGGER.info("🔹 Registering user: {}", authUserDTO.getEmail());
        return ResponseEntity.ok(authenticationService.register(authUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        LOGGER.info("🔹 User login attempt: {}", loginDTO.getEmail());
        String response = authenticationService.login(loginDTO);
        LOGGER.info("🔹 Login response: {}", response);
        return ResponseEntity.ok(response);
    }

    // Forgot Password Endpoint
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email, @RequestBody Map<String, String> request) {
        LOGGER.info("🔹 Forgot Password request for email: {}", email);
        String response = authenticationService.forgotPassword(email, request.get("password"));
        LOGGER.info("🔹 Forgot Password response: {}", response);
        return ResponseEntity.ok(response);
    }

    // Reset Password Endpoint
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email,
                                                @RequestParam String currentPassword,
                                                @RequestParam String newPassword) {
        LOGGER.info("🔹 Reset Password attempt for email: {}", email);
        String response = authenticationService.resetPassword(email, currentPassword, newPassword);
        LOGGER.info("🔹 Reset Password response: {}", response);
        return ResponseEntity.ok(response);
    }
}
