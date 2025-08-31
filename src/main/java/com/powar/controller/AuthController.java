package com.powar.controller;

import com.powar.dto.AuthRequest;
import com.powar.dto.AuthResponse;
import com.powar.dto.RegisterRequest;
import com.powar.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * User login endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            logger.info("Login attempt for user: {}", authRequest.getUsername());
            
            AuthResponse authResponse = authService.login(authRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("data", authResponse);
            response.put("source", "java-backend");
            
            logger.info("Login successful for user: {}", authRequest.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Login failed for user: {}", authRequest.getUsername(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Login failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * User registration endpoint
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            logger.info("Registration attempt for user: {}", registerRequest.getUsername());
            logger.info("Registration data: email={}, mobile={}, firstName={}, lastName={}", 
                registerRequest.getEmail_id(), registerRequest.getMobile_no(), 
                registerRequest.getFirst_name(), registerRequest.getLast_name());
            
            AuthResponse authResponse = authService.register(registerRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Registration successful");
            response.put("data", authResponse);
            response.put("source", "java-backend");
            
            logger.info("Registration successful for user: {}", registerRequest.getUsername());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Registration failed for user: {}", registerRequest.getUsername(), e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Registration failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            errorResponse.put("error", e.getClass().getSimpleName());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Refresh token endpoint
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Token refresh attempt");
            
            AuthResponse authResponse = authService.refreshToken(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Token refreshed successfully");
            response.put("data", authResponse);
            response.put("source", "java-backend");
            
            logger.info("Token refreshed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Token refresh failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Token refresh failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Refresh token endpoint (alternative path for frontend compatibility)
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshTokenAlt(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            if (refreshToken == null) {
                throw new IllegalArgumentException("Refresh token is required");
            }
            
            logger.info("Token refresh attempt via refresh-token endpoint");
            
            AuthResponse authResponse = authService.refreshToken(refreshToken);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Token refreshed successfully");
            response.put("data", authResponse);
            response.put("source", "java-backend");
            
            logger.info("Token refreshed successfully");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Token refresh failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Token refresh failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Store phone number for unauthenticated users
     */
    @PostMapping("/store-phone")
    public ResponseEntity<Map<String, Object>> storePhoneNumber(@RequestBody Map<String, String> request) {
        try {
            String mobileNo = request.get("mobile_no");
            if (mobileNo == null) {
                throw new IllegalArgumentException("Mobile number is required");
            }
            
            logger.info("Store phone number attempt for: {}", mobileNo);
            
            // This would typically create a temporary user or session
            // For now, we'll return a mock response
            Map<String, Object> userData = new HashMap<>();
            userData.put("mobile_no", mobileNo);
            userData.put("id", 0); // Temporary ID
            userData.put("email_id", null);
            userData.put("role", "USER");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Phone number stored successfully");
            response.put("data", Map.of("user", userData));
            response.put("source", "java-backend");
            
            logger.info("Phone number stored successfully for: {}", mobileNo);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Store phone number failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Store phone number failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Complete user profile for users without passwords
     */
    @PostMapping("/complete-profile")
    public ResponseEntity<Map<String, Object>> completeProfile(@RequestBody Map<String, Object> request) {
        try {
            logger.info("Complete profile attempt for user ID: {}", request.get("user_id"));
            
            // This would typically update the user profile
            // For now, we'll return a mock response
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", request.get("user_id"));
            userData.put("email_id", null);
            userData.put("mobile_no", request.get("mobile_no"));
            userData.put("role", "USER");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Profile completed successfully");
            response.put("data", Map.of("user", userData));
            response.put("source", "java-backend");
            
            logger.info("Profile completed successfully for user ID: {}", request.get("user_id"));
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Complete profile failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Complete profile failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Logout endpoint
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Logout attempt");
            
            authService.logout(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Logout successful");
            response.put("source", "java-backend");
            
            logger.info("Logout successful");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Logout failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Logout failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Validate token endpoint
     */
    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid authorization header");
            }
            
            String token = authHeader.substring(7);
            logger.info("Token validation attempt");
            
            boolean isValid = authService.validateToken(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Token validation completed");
            response.put("data", Map.of("valid", isValid));
            response.put("source", "java-backend");
            
            logger.info("Token validation completed. Valid: {}", isValid);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Token validation failed", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Token validation failed: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/success")
    String test() {
        return "success";
    }
}
