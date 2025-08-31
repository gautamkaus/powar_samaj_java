package com.powar.repository;

import com.powar.entity.MasterDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterDistrictRepository extends JpaRepository<MasterDistrict, Long> {
    
    /**
     * Find districts by state ID
     */
    List<MasterDistrict> findByStateIdOrderByDistNameAsc(Long stateId);
    
    /**
     * Find district by name
     */
    Optional<MasterDistrict> findByDistName(String distName);
    
    /**
     * Find districts by state ID and name containing (case-insensitive)
     */
    @Query("SELECT d FROM MasterDistrict d WHERE d.state.id = :stateId AND LOWER(d.distName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MasterDistrict> findByStateIdAndDistNameContainingIgnoreCase(@Param("stateId") Long stateId, @Param("name") String name);
    
    /**
     * Check if district exists by name in a specific state
     */
    @Query("SELECT COUNT(d) > 0 FROM MasterDistrict d WHERE d.state.id = :stateId AND d.distName = :distName")
    boolean existsByStateIdAndDistName(@Param("stateId") Long stateId, @Param("distName") String distName);
}
