package com.student.dto;

public class Login_DTO {

    private String username;
    private String role;
    private String token;
    private String password;

    public Login_DTO() {
    }

    // Login response
    public Login_DTO(String username, String role, String token) {
        this.username = username;
        this.role = role;
        this.token = token;
    }

    // Login request
    public Login_DTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
