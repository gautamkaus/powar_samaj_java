package com.powar.controller;

import com.powar.entity.MasterState;
import com.powar.entity.MasterDistrict;
import com.powar.entity.MasterTahsil;
import com.powar.entity.MasterProfession;
import com.powar.repository.MasterStateRepository;
import com.powar.repository.MasterDistrictRepository;
import com.powar.repository.MasterTahsilRepository;
import com.powar.repository.MasterProfessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
@CrossOrigin(origins = "*")
public class DataController {
    
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    
    private final MasterStateRepository masterStateRepository;
    private final MasterDistrictRepository masterDistrictRepository;
    private final MasterTahsilRepository masterTahsilRepository;
    private final MasterProfessionRepository masterProfessionRepository;
    
    @Autowired
    public DataController(MasterStateRepository masterStateRepository,
                         MasterDistrictRepository masterDistrictRepository,
                         MasterTahsilRepository masterTahsilRepository,
                         MasterProfessionRepository masterProfessionRepository) {
        this.masterStateRepository = masterStateRepository;
        this.masterDistrictRepository = masterDistrictRepository;
        this.masterTahsilRepository = masterTahsilRepository;
        this.masterProfessionRepository = masterProfessionRepository;
    }
    
    /**
     * Test endpoint to verify security configuration
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        logger.info("Test endpoint accessed");
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Test endpoint working");
        response.put("source", "java-backend");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.info("Health check endpoint accessed");
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Data controller is healthy");
        response.put("status", "UP");
        response.put("source", "java-backend");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get all states
     */
    @GetMapping("/states")
    public ResponseEntity<Map<String, Object>> getAllStates() {
        try {
            logger.info("Fetching all states");
            
            List<MasterState> states = masterStateRepository.findAllByOrderByStateNameAsc();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "States fetched successfully");
            response.put("data", states);
            response.put("count", states.size());
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched {} states", states.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching states", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch states: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Get districts by state ID
     */
    @GetMapping("/states/{stateId}/districts")
    public ResponseEntity<Map<String, Object>> getDistrictsByState(@PathVariable Long stateId) {
        try {
            logger.info("Fetching districts for state ID: {}", stateId);
            
            List<MasterDistrict> districts = masterDistrictRepository.findByStateIdOrderByDistNameAsc(stateId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Districts fetched successfully");
            response.put("data", districts);
            response.put("count", districts.size());
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched {} districts for state {}", districts.size(), stateId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching districts for state {}", stateId, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch districts: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Get tahsils by district ID
     */
    @GetMapping("/districts/{districtId}/tahsils")
    public ResponseEntity<Map<String, Object>> getTahsilsByDistrict(@PathVariable Long districtId) {
        try {
            logger.info("Fetching tahsils for district ID: {}", districtId);
            
            List<MasterTahsil> tahsils = masterTahsilRepository.findByDistrictIdOrderByTahsilNameAsc(districtId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Tahsils fetched successfully");
            response.put("data", tahsils);
            response.put("count", tahsils.size());
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched {} tahsils for district {}", tahsils.size(), districtId);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching tahsils for district {}", districtId, e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch tahsils: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Get all professions
     */
    @GetMapping("/professions")
    public ResponseEntity<Map<String, Object>> getAllProfessions() {
        try {
            logger.info("Fetching all professions");
            
            List<MasterProfession> professions = masterProfessionRepository.findAllByOrderByEmployeeTypeAsc();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Professions fetched successfully");
            response.put("data", professions);
            response.put("count", professions.size());
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched {} professions", professions.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching professions", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch professions: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    /**
     * Get complete location hierarchy
     */
    @GetMapping("/location-hierarchy")
    public ResponseEntity<Map<String, Object>> getLocationHierarchy() {
        try {
            logger.info("Fetching complete location hierarchy");
            
            List<MasterState> states = masterStateRepository.findAllByOrderByStateNameAsc();
            
            // Build hierarchy structure
            for (MasterState state : states) {
                List<MasterDistrict> districts = masterDistrictRepository.findByStateIdOrderByDistNameAsc(state.getId());
                state.setDistricts(districts);
                
                for (MasterDistrict district : districts) {
                    List<MasterTahsil> tahsils = masterTahsilRepository.findByDistrictIdOrderByTahsilNameAsc(district.getId());
                    district.setTahsils(tahsils);
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Location hierarchy fetched successfully");
            response.put("data", states);
            response.put("count", states.size());
            response.put("source", "java-backend");
            
            logger.info("Successfully fetched location hierarchy with {} states", states.size());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error fetching location hierarchy", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Failed to fetch location hierarchy: " + e.getMessage());
            errorResponse.put("source", "java-backend");
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
