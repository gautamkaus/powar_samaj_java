package com.powar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.powar.config.ZeroDateTimeConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master_dist")
public class MasterDistrict {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_state_id", nullable = false)
    @JsonBackReference("state-districts")
    private MasterState state;
    
    @NotBlank
    @Column(name = "dist_name", nullable = false, length = 150)
    @JsonProperty("dist_name")
    private String distName;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = true, updatable = false)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("district-tahsils")
    private List<MasterTahsil> tahsils = new ArrayList<>();
    
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("district-profiles")
    private List<UserProfile> userProfiles = new ArrayList<>();
    
    // Constructors
    public MasterDistrict() {}
    
    public MasterDistrict(MasterState state, String distName) {
        this.state = state;
        this.distName = distName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public MasterState getState() {
        return state;
    }
    
    public void setState(MasterState state) {
        this.state = state;
    }
    
    public String getDistName() {
        return distName;
    }
    
    public void setDistName(String distName) {
        this.distName = distName;
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
    
    public List<MasterTahsil> getTahsils() {
        return tahsils;
    }
    
    public void setTahsils(List<MasterTahsil> tahsils) {
        this.tahsils = tahsils;
    }
    
    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }
    
    public void setUserProfiles(List<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
    
    @Override
    public String toString() {
        return "MasterDistrict{" +
                "id=" + id +
                ", distName='" + distName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
