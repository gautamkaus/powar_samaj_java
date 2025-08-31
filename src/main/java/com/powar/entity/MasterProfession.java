package com.powar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.powar.config.ZeroDateTimeConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master_profession")
public class MasterProfession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    @JsonProperty("employee_type")
    private EmployeeType employeeType;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = true, updatable = false)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "profession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("profession-profiles")
    private List<UserProfile> userProfiles = new ArrayList<>();
    
    // Constructors
    public MasterProfession() {}
    
    public MasterProfession(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    
    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }
    
    public void setUserProfiles(List<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
    
    // EmployeeType enum
    public enum EmployeeType {
        PRIVATE, GOVERNMENT, SELF_EMPLOYED, BUSINESS
    }
    
    @Override
    public String toString() {
        return "MasterProfession{" +
                "id=" + id +
                ", employeeType=" + employeeType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
