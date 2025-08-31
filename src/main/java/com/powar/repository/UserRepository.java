package com.powar.repository;

import com.powar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email ID
     */
    Optional<User> findByEmailId(String emailId);
    
    /**
     * Find user by mobile number
     */
    Optional<User> findByMobileNo(String mobileNo);
    
    /**
     * Check if user exists by email ID
     */
    boolean existsByEmailId(String emailId);
    
    /**
     * Check if user exists by mobile number
     */
    boolean existsByMobileNo(String mobileNo);
    
    /**
     * Find user by email ID or mobile number
     */
    @Query("SELECT u FROM User u WHERE u.emailId = :identifier OR u.mobileNo = :identifier")
    Optional<User> findByEmailIdOrMobileNo(@Param("identifier") String identifier);
    
    /**
     * Check if user exists by email ID or mobile number
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.emailId = :identifier OR u.mobileNo = :identifier")
    boolean existsByEmailIdOrMobileNo(@Param("identifier") String identifier);
}
