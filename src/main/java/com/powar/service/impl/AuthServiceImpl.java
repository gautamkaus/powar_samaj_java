package com.powar.service.impl;

import com.powar.dto.AuthRequest;
import com.powar.dto.AuthResponse;
import com.powar.dto.RegisterRequest;
import com.powar.entity.User;
import com.powar.entity.UserProfile;
import com.powar.entity.MasterState;
import com.powar.entity.MasterDistrict;
import com.powar.entity.MasterTahsil;
import com.powar.entity.MasterProfession;
import com.powar.repository.UserRepository;
import com.powar.repository.UserProfileRepository;
import com.powar.repository.MasterStateRepository;
import com.powar.repository.MasterDistrictRepository;
import com.powar.repository.MasterTahsilRepository;
import com.powar.repository.MasterProfessionRepository;
import com.powar.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final MasterStateRepository masterStateRepository;
    private final MasterDistrictRepository masterDistrictRepository;
    private final MasterTahsilRepository masterTahsilRepository;
    private final MasterProfessionRepository masterProfessionRepository;

    // In-memory storage for refresh tokens and blacklisted tokens (in production, use Redis)
    private final Map<String, String> refreshTokens = new ConcurrentHashMap<>();
    private final Map<String, Boolean> blacklistedTokens = new ConcurrentHashMap<>();
    
    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         UserProfileRepository userProfileRepository,
                         MasterStateRepository masterStateRepository,
                         MasterDistrictRepository masterDistrictRepository,
                         MasterTahsilRepository masterTahsilRepository,
                         MasterProfessionRepository masterProfessionRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.masterStateRepository = masterStateRepository;
        this.masterDistrictRepository = masterDistrictRepository;
        this.masterTahsilRepository = masterTahsilRepository;
        this.masterProfessionRepository = masterProfessionRepository;
    }
    
    @Override
    public AuthResponse login(AuthRequest authRequest) {
        try {
            logger.info("Processing login for user: {}", authRequest.getUsername());
            
            // Find user by email or mobile number
            User user = userRepository.findByEmailIdOrMobileNo(authRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Invalid username or password"));
            
            // Check if password matches
            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
                throw new RuntimeException("Invalid username or password");
            }
            
            // Get user profile
            UserProfile userProfile = userProfileRepository.findByUserId(user.getId())
                    .orElse(null);
            
            // Create user info
            AuthResponse.UserInfo userInfo = createUserInfo(user, userProfile);
            
            // Generate JWT tokens
            String accessToken = generateAccessToken(userInfo);
            String refreshToken = generateRefreshToken(userInfo);
            
            // Store refresh token
            refreshTokens.put(refreshToken, userInfo.getUsername());
            
            logger.info("Login successful for user: {}", authRequest.getUsername());
            
            return new AuthResponse(accessToken, refreshToken, "Bearer", jwtExpiration, userInfo);
            
        } catch (Exception e) {
            logger.error("Login failed for user: {}", authRequest.getUsername(), e);
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            logger.info("Processing registration for user: {}", registerRequest.getEmail_id());
            
            // Check if user already exists by email or mobile
            if (userRepository.existsByEmailIdOrMobileNo(registerRequest.getEmail_id()) || 
                userRepository.existsByEmailIdOrMobileNo(registerRequest.getMobile_no())) {
                throw new RuntimeException("User already exists with this email or mobile number");
            }
            
                        // Create and save user
            User user = new User();
            user.setEmailId(registerRequest.getEmail_id());
            user.setMobileNo(registerRequest.getMobile_no());
            // Handle both password and password_hash fields
            String password = registerRequest.getPassword_hash();
            if (password == null || password.trim().isEmpty()) {
                throw new RuntimeException("Password is required");
            }
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setRole(User.UserRole.USER);
            
            logger.info("Attempting to save user with email: {}", registerRequest.getEmail_id());
            user = userRepository.save(user);
            logger.info("User saved successfully with ID: {}", user.getId());
            
            // Create and save user profile
            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setUserId(user.getId());
            userProfile.setFirstName(registerRequest.getFirst_name());
            userProfile.setLastName(registerRequest.getLast_name());
            if (Objects.nonNull(registerRequest.getGender())) {
                userProfile.setGender(UserProfile.Gender.valueOf(registerRequest.getGender().toUpperCase()));
            }
            // Set optional fields if provided
            if (registerRequest.getMiddle_name() != null && !registerRequest.getMiddle_name().trim().isEmpty()) {
                userProfile.setMiddleName(registerRequest.getMiddle_name());
            }
            
            if (registerRequest.getDob() != null) {
                userProfile.setDob(LocalDate.parse(registerRequest.getDob()));
            }
            
            if (registerRequest.getAbout() != null && !registerRequest.getAbout().trim().isEmpty()) {
                userProfile.setAbout(registerRequest.getAbout());
            }
            
            // Set location if provided
            if (registerRequest.getState_id() != null) {
                MasterState state = masterStateRepository.findById(registerRequest.getState_id())
                        .orElseThrow(() -> new RuntimeException("Invalid state ID: " + registerRequest.getState_id()));
                userProfile.setState(state);
                
                if (registerRequest.getDistrict_id() != null) {
                    MasterDistrict district = masterDistrictRepository.findById(registerRequest.getDistrict_id())
                            .orElseThrow(() -> new RuntimeException("Invalid district ID: " + registerRequest.getDistrict_id()));
                    userProfile.setDistrict(district);
                    
                    if (registerRequest.getTahsil_id() != null) {
                        MasterTahsil tahsil = masterTahsilRepository.findById(registerRequest.getTahsil_id())
                                .orElseThrow(() -> new RuntimeException("Invalid tahsil ID: " + registerRequest.getTahsil_id()));
                        userProfile.setTahsil(tahsil);
                    }
                }
            }
            
            // Set profession if provided
            if (registerRequest.getProfession_id() != null && !registerRequest.getProfession_id().trim().isEmpty()) {
                try {
                    Long professionId = Long.parseLong(registerRequest.getProfession_id());
                    MasterProfession profession = masterProfessionRepository.findById(professionId)
                            .orElseThrow(() -> new RuntimeException("Invalid profession ID"));
                    userProfile.setProfession(profession);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid profession ID format");
                }
            }
            
            if (registerRequest.getBusiness_description() != null && !registerRequest.getBusiness_description().trim().isEmpty()) {
                userProfile.setBusinessDescription(registerRequest.getBusiness_description());
            }
            
            logger.info("Saving user profile for user ID: {}", user.getId());
            userProfileRepository.save(userProfile);
//            user.setUserProfile(userProfile);
//            user = userRepository.save(user);
            logger.info("User profile saved successfully");
            
            // Create user info
            AuthResponse.UserInfo userInfo = createUserInfo(user, userProfile);
            
            // Generate JWT tokens
            String accessToken = generateAccessToken(userInfo);
            String refreshToken = generateRefreshToken(userInfo);
            
            // Store refresh token
            refreshTokens.put(refreshToken, userInfo.getUsername());
            
            logger.info("Registration successful for user: {}", registerRequest.getEmail_id());
            
            return new AuthResponse(accessToken, refreshToken, "Bearer", jwtExpiration, userInfo);
            
        } catch (Exception e) {
            logger.error("Registration failed for user: {}", registerRequest.getEmail_id(), e);
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }
    
    @Override
    public AuthResponse refreshToken(String refreshToken) {
        try {
            logger.info("Processing token refresh");
            
            // Validate refresh token
            if (!refreshTokens.containsKey(refreshToken)) {
                throw new RuntimeException("Invalid refresh token");
            }
            
            String username = refreshTokens.get(refreshToken);
            
            // Find user
            User user = userRepository.findByEmailIdOrMobileNo(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            UserProfile userProfile = userProfileRepository.findByUserId(user.getId()).orElse(null);
            
            // Create user info
            AuthResponse.UserInfo userInfo = createUserInfo(user, userProfile);
            
            // Generate new JWT tokens
            String newAccessToken = generateAccessToken(userInfo);
            String newRefreshToken = generateRefreshToken(userInfo);
            
            // Remove old refresh token and store new one
            refreshTokens.remove(refreshToken);
            refreshTokens.put(newRefreshToken, username);
            
            logger.info("Token refresh successful for user: {}", username);
            
            return new AuthResponse(newAccessToken, newRefreshToken, "Bearer", jwtExpiration, userInfo);
            
        } catch (Exception e) {
            logger.error("Token refresh failed", e);
            throw new RuntimeException("Token refresh failed: " + e.getMessage());
        }
    }
    
    @Override
    public void logout(String token) {
        try {
            logger.info("Processing logout");
            
            // Add token to blacklist
            blacklistedTokens.put(token, true);
            
            // Extract username from token and remove refresh token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            String username = claims.getSubject();
            
            // Remove refresh token for this user
            refreshTokens.entrySet().removeIf(entry -> entry.getValue().equals(username));
            
            logger.info("Logout successful for user: {}", username);
            
        } catch (Exception e) {
            logger.error("Logout failed", e);
            throw new RuntimeException("Logout failed: " + e.getMessage());
        }
    }
    
    @Override
    public boolean validateToken(String token) {
        try {
            // Check if token is blacklisted
            if (blacklistedTokens.containsKey(token)) {
                return false;
            }
            
            // Validate JWT token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            // Check if token is expired
            return !claims.getExpiration().before(new Date());
            
        } catch (Exception e) {
            logger.error("Token validation failed", e);
            return false;
        }
    }
    
    private String generateAccessToken(AuthResponse.UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userInfo.getId());
        claims.put("username", userInfo.getUsername());
        claims.put("email", userInfo.getEmail());
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userInfo.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    private String generateRefreshToken(AuthResponse.UserInfo userInfo) {
        return Jwts.builder()
                .setSubject(userInfo.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (jwtExpiration * 7))) // 7 times longer
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    private AuthResponse.UserInfo createUserInfo(User user, UserProfile userProfile) {
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                user.getId(),
                user.getEmailId(),
                user.getEmailId(),
                userProfile != null ? userProfile.getFirstName() : "User",
                userProfile != null ? userProfile.getLastName() : "Name"
        );
        
        if (userProfile != null) {
            userInfo.setBio(userProfile.getAbout());
            if (userProfile.getState() != null) {
                userInfo.setCommunityId(userProfile.getState().getId());
                userInfo.setCommunityName(userProfile.getState().getStateName());
            }
        }
        
        return userInfo;
    }
}
