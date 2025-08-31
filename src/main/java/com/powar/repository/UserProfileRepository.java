package com.powar.repository;

import com.powar.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    
    /**
     * Find user profile by user ID
     */
    Optional<UserProfile> findByUserId(Long userId);
    
    /**
     * Find user profiles by state ID
     */
    @Query("SELECT up FROM UserProfile up WHERE up.state.id = :stateId")
    List<UserProfile> findByStateId(@Param("stateId") Long stateId);
    
    /**
     * Find user profiles by district ID
     */
    @Query("SELECT up FROM UserProfile up WHERE up.district.id = :districtId")
    List<UserProfile> findByDistrictId(@Param("districtId") Long districtId);
    
    /**
     * Find user profiles by tahsil ID
     */
    @Query("SELECT up FROM UserProfile up WHERE up.tahsil.id = :tahsilId")
    List<UserProfile> findByTahsilId(@Param("tahsilId") Long tahsilId);
    
    /**
     * Find user profiles by profession ID
     */
    @Query("SELECT up FROM UserProfile up WHERE up.profession.id = :professionId")
    List<UserProfile> findByProfessionId(@Param("professionId") Long professionId);
    
    /**
     * Find user profiles by gender
     */
    List<UserProfile> findByGender(UserProfile.Gender gender);
    
    /**
     * Find user profiles by state ID and gender
     */
    @Query("SELECT up FROM UserProfile up WHERE up.state.id = :stateId AND up.gender = :gender")
    List<UserProfile> findByStateIdAndGender(@Param("stateId") Long stateId, @Param("gender") UserProfile.Gender gender);
}
