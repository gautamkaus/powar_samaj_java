package com.powar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email_id;
    
    @NotBlank(message = "Password is required")
    private String password_hash;
    
    // Default constructor
    public AuthRequest() {}
    
    // Constructor with parameters
    public AuthRequest(String email_id, String password_hash) {
        this.email_id = email_id;
        this.password_hash = password_hash;
    }
    
    // Getters and Setters
    public String getEmail_id() {
        return email_id;
    }
    
    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
    
    public String getPassword_hash() {
        return password_hash;
    }
    
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
    
    @Override
    public String toString() {
        return "AuthRequest{" +
                "email_id='" + email_id + '\'' +
                ", password_hash='[PROTECTED]'" +
                '}';
    }
}
