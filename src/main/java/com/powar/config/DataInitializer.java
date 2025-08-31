package com.powar.config;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    private final MasterStateRepository masterStateRepository;
    private final MasterDistrictRepository masterDistrictRepository;
    private final MasterTahsilRepository masterTahsilRepository;
    private final MasterProfessionRepository masterProfessionRepository;
    
    @Autowired
    public DataInitializer(MasterStateRepository masterStateRepository,
                          MasterDistrictRepository masterDistrictRepository,
                          MasterTahsilRepository masterTahsilRepository,
                          MasterProfessionRepository masterProfessionRepository) {
        this.masterStateRepository = masterStateRepository;
        this.masterDistrictRepository = masterDistrictRepository;
        this.masterTahsilRepository = masterTahsilRepository;
        this.masterProfessionRepository = masterProfessionRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting data initialization...");
        
        try {
            initializeProfessions();
            initializeStatesAndDistricts();
            logger.info("Data initialization completed successfully");
        } catch (Exception e) {
            logger.error("Error during data initialization", e);
        }
    }
    
    private void initializeProfessions() {
        if (masterProfessionRepository.count() == 0) {
            logger.info("Initializing professions...");
            
            List<MasterProfession> professions = Arrays.asList(
                new MasterProfession(MasterProfession.EmployeeType.PRIVATE),
                new MasterProfession(MasterProfession.EmployeeType.GOVERNMENT),
                new MasterProfession(MasterProfession.EmployeeType.SELF_EMPLOYED),
                new MasterProfession(MasterProfession.EmployeeType.BUSINESS)
            );
            
            masterProfessionRepository.saveAll(professions);
            logger.info("Professions initialized: {}", professions.size());
        }
    }
    
    private void initializeStatesAndDistricts() {
        if (masterStateRepository.count() == 0) {
            logger.info("Initializing states and districts...");
            
            // Maharashtra
            MasterState maharashtra = new MasterState("Maharashtra");
            maharashtra = masterStateRepository.save(maharashtra);
            
            List<MasterDistrict> maharashtraDistricts = Arrays.asList(
                new MasterDistrict(maharashtra, "Mumbai"),
                new MasterDistrict(maharashtra, "Pune"),
                new MasterDistrict(maharashtra, "Nagpur"),
                new MasterDistrict(maharashtra, "Thane"),
                new MasterDistrict(maharashtra, "Nashik")
            );
            
            masterDistrictRepository.saveAll(maharashtraDistricts);
            
            // Delhi
            MasterState delhi = new MasterState("Delhi");
            delhi = masterStateRepository.save(delhi);
            
            List<MasterDistrict> delhiDistricts = Arrays.asList(
                new MasterDistrict(delhi, "New Delhi"),
                new MasterDistrict(delhi, "Central Delhi"),
                new MasterDistrict(delhi, "South Delhi"),
                new MasterDistrict(delhi, "North Delhi"),
                new MasterDistrict(delhi, "East Delhi")
            );
            
            masterDistrictRepository.saveAll(delhiDistricts);
            
            // Karnataka
            MasterState karnataka = new MasterState("Karnataka");
            karnataka = masterStateRepository.save(karnataka);
            
            List<MasterDistrict> karnatakaDistricts = Arrays.asList(
                new MasterDistrict(karnataka, "Bangalore"),
                new MasterDistrict(karnataka, "Mysore"),
                new MasterDistrict(karnataka, "Mangalore"),
                new MasterDistrict(karnataka, "Hubli"),
                new MasterDistrict(karnataka, "Belgaum")
            );
            
            masterDistrictRepository.saveAll(karnatakaDistricts);
            
            // Tamil Nadu
            MasterState tamilNadu = new MasterState("Tamil Nadu");
            tamilNadu = masterStateRepository.save(tamilNadu);
            
            List<MasterDistrict> tamilNaduDistricts = Arrays.asList(
                new MasterDistrict(tamilNadu, "Chennai"),
                new MasterDistrict(tamilNadu, "Coimbatore"),
                new MasterDistrict(tamilNadu, "Madurai"),
                new MasterDistrict(tamilNadu, "Salem"),
                new MasterDistrict(tamilNadu, "Vellore")
            );
            
            masterDistrictRepository.saveAll(tamilNaduDistricts);
            
            logger.info("States and districts initialized: {} states, {} districts", 
                       masterStateRepository.count(), masterDistrictRepository.count());
        }
    }
}
