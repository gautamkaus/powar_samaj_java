package com.powar.repository;

import com.powar.entity.MasterProfession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterProfessionRepository extends JpaRepository<MasterProfession, Long> {
    
    /**
     * Find profession by employee type
     */
    Optional<MasterProfession> findByEmployeeType(MasterProfession.EmployeeType employeeType);
    
    /**
     * Find all professions ordered by employee type
     */
    List<MasterProfession> findAllByOrderByEmployeeTypeAsc();
    
    /**
     * Check if profession exists by employee type
     */
    boolean existsByEmployeeType(MasterProfession.EmployeeType employeeType);
}
