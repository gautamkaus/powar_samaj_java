package com.powar.repository;

import com.powar.entity.MasterState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterStateRepository extends JpaRepository<MasterState, Long> {
    
    /**
     * Find state by name
     */
    Optional<MasterState> findByStateName(String stateName);
    
    /**
     * Find all states ordered by name
     */
    List<MasterState> findAllByOrderByStateNameAsc();
    
    /**
     * Check if state exists by name
     */
    boolean existsByStateName(String stateName);
    
    /**
     * Find states by name containing (case-insensitive)
     */
    @Query("SELECT s FROM MasterState s WHERE LOWER(s.stateName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MasterState> findByStateNameContainingIgnoreCase(@org.springframework.data.repository.query.Param("name") String name);
}
