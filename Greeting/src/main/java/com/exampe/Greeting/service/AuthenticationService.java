package com.exampe.Greeting.service;

import com.exampe.Greeting.dto.AuthUserDTO;
import com.exampe.Greeting.dto.LoginDTO;
import com.exampe.Greeting.model.AuthUser;
import com.exampe.Greeting.repository.AuthUserRepository;
import com.exampe.Greeting.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthUserRepository authUserRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationService(AuthUserRepository authUserRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authUserRepository = authUserRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public String register(AuthUserDTO authUserDTO) {
        if (authUserRepository.findByEmail(authUserDTO.getEmail()).isPresent()) {
            return "Email is already in use.";
        }

        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName());
        user.setEmail(authUserDTO.getEmail());

        // ✅ Ensure password is hashed before saving
        String hashedPassword = passwordEncoder.encode(authUserDTO.getPassword());
        user.setPassword(hashedPassword);
        authUserRepository.save(user);
        return "User registered successfully!";
    }


    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            System.out.println("🚨 User not found: " + loginDTO.getEmail());
            return "User not found!";
        }

        AuthUser user = userOptional.get();
        System.out.println("🔹 DEBUG: Found user with email: " + user.getEmail());
        System.out.println("🔹 DEBUG: Hashed password in DB: " + user.getPassword());
        System.out.println("🔹 DEBUG: Entered password before hashing: '" + loginDTO.getPassword() + "'");

        // ✅ Trim input to remove accidental spaces
        boolean passwordMatches = passwordEncoder.matches(loginDTO.getPassword().trim(), user.getPassword());
        System.out.println("🔹 DEBUG: Password match result: " + passwordMatches);

        if (!passwordMatches) {
            return "Invalid email or password!";
        }

        return "Login Successfully";
    }



    // Forgot Password - User provides a new password
    public String forgotPassword(String email, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Sorry! We cannot find the user email: " + email;
        }
        AuthUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword)); // Hash new password
        authUserRepository.save(user);

        // Send confirmation email
        emailService.sendSimpleEmail(email, "Password Reset Successful", "Your password has been updated successfully.");
        return "Password has been changed successfully!";
    }

    // Reset Password - User provides current and new password
    public String resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "User not found with email: " + email;
        }
        AuthUser user = userOptional.get();

        // Validate the current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "Current password is incorrect!";
        }

        // Hash the new password and update
        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);
        return "Password reset successfully!";
    }
}
