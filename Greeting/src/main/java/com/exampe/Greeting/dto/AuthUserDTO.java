package com.exampe.Greeting.dto;

import jakarta.validation.constraints.*;

public class AuthUserDTO {
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "First name must start with an uppercase letter.")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Last name must start with an uppercase letter.")
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[@#$%^&*()-+=])(?=.*[0-9]).{8,}$",
            message = "Password must contain at least 1 uppercase letter, 1 special character, 1 number, and be at least 8 characters long."
    )
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
