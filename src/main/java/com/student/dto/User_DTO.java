package com.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User_DTO {
    private Long id;
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50)
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100)
    private String password;
    private String role;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    private boolean enabled ;

    public User_DTO() {
    }

    public User_DTO(Long id, String username, String password, String role, String email, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
