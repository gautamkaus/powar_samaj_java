package com.powar.repository;

import com.powar.entity.MasterTahsil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterTahsilRepository extends JpaRepository<MasterTahsil, Long> {
    
    /**
     * Find tahsils by district ID
     */
    List<MasterTahsil> findByDistrictIdOrderByTahsilNameAsc(Long districtId);
    
    /**
     * Find tahsil by name
     */
    Optional<MasterTahsil> findByTahsilName(String tahsilName);
    
    /**
     * Find tahsils by district ID and name containing (case-insensitive)
     */
    @Query("SELECT t FROM MasterTahsil t WHERE t.district.id = :districtId AND LOWER(t.tahsilName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MasterTahsil> findByDistrictIdAndTahsilNameContainingIgnoreCase(@Param("districtId") Long districtId, @Param("name") String name);
    
    /**
     * Check if tahsil exists by name in a specific district
     */
    @Query("SELECT COUNT(t) > 0 FROM MasterTahsil t WHERE t.district.id = :districtId AND t.tahsilName = :tahsilName")
    boolean existsByDistrictIdAndTahsilName(@Param("districtId") Long districtId, @Param("tahsilName") String tahsilName);
}
