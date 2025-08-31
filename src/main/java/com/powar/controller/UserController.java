package com.powar.controller;

import com.powar.entity.User;
import com.powar.entity.UserProfile;
import com.powar.repository.UserRepository;
import com.powar.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    
    @Autowired
    public UserController(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }
    
    /**
     * Get user profile
     */
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Get user profile attempt");
            
            // For now, return a mock response
            // In a real implementation, you would decode the JWT token and get the user ID
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", 1);
            userData.put("email_id", "user@example.com");
            userData.put("mobile_no", "1234567890");
            userData.put("role", "USER");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User profile fetched successfully");
            response.put("data", Map.of("user", userData));
            response.put("source", "java-backend");
            
            logger.info("User profile fetched successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Get user profile failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Get user profile failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Update user profile
     */
    @PutMapping("/profile")
    public ResponseEntity<Map<String, Object>> updateUserProfile(@RequestHeader("Authorization") String authHeader, 
                                                               @RequestBody Map<String, Object> profileData) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Update user profile attempt");
            
            // For now, return a mock response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User profile updated successfully");
            response.put("data", Map.of("profile", profileData));
            response.put("source", "java-backend");
            
            logger.info("User profile updated successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Update user profile failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Update user profile failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Delete user profile
     */
    @DeleteMapping("/profile")
    public ResponseEntity<Map<String, Object>> deleteUserProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Delete user profile attempt");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User profile deleted successfully");
            response.put("source", "java-backend");
            
            logger.info("User profile deleted successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Delete user profile failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Delete user profile failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
