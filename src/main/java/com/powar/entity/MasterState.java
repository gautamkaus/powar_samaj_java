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
@Table(name = "master_state")
public class MasterState {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "state_name", nullable = false, length = 150)
    @JsonProperty("state_name")
    private String stateName;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = true, updatable = false)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime updatedAt;
    
//    // Relationships
//    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference("state-districts")
//    private List<MasterDistrict> districts = new ArrayList<>();
//
//    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference("state-profiles")
//    private List<UserProfile> userProfiles = new ArrayList<>();
    
    // Constructors
    public MasterState() {}
    
    public MasterState(String stateName) {
        this.stateName = stateName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStateName() {
        return stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
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
    
    @Override
    public String toString() {
        return "MasterState{" +
                "id=" + id +
                ", stateName='" + stateName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
