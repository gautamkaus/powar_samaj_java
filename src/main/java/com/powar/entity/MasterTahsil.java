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
@Table(name = "master_tahsil")
public class MasterTahsil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_dist_id", nullable = false)
    @JsonBackReference("district-tahsils")
    private MasterDistrict district;
    
    @NotBlank
    @Column(name = "tahsil_name", nullable = false, length = 150)
    @JsonProperty("tahsil_name")
    private String tahsilName;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = true, updatable = false)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "tahsil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("tahsil-profiles")
    private List<UserProfile> userProfiles = new ArrayList<>();
    
    // Constructors
    public MasterTahsil() {}
    
    public MasterTahsil(MasterDistrict district, String tahsilName) {
        this.district = district;
        this.tahsilName = tahsilName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public MasterDistrict getDistrict() {
        return district;
    }
    
    public void setDistrict(MasterDistrict district) {
        this.district = district;
    }
    
    public String getTahsilName() {
        return tahsilName;
    }
    
    public void setTahsilName(String tahsilName) {
        this.tahsilName = tahsilName;
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
    
    @Override
    public String toString() {
        return "MasterTahsil{" +
                "id=" + id +
                ", tahsilName='" + tahsilName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
