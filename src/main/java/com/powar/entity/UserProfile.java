package com.powar.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.powar.mapping.EntityFieldMapping;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.powar.config.ZeroDateTimeConverter;
import com.powar.config.ZeroDateConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_profile")
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @EntityFieldMapping(referencePath = "user.id")
    private Long userId;

    @NotNull
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private User user;
    
    @NotBlank
    @Column(name = "first_name", nullable = false, length = 100)
    @JsonProperty("first_name")
    private String firstName;
    
    @Column(name = "middle_name", length = 100)
    @JsonProperty("middle_name")
    private String middleName;
    
    @NotBlank
    @Column(name = "last_name", nullable = false, length = 100)
    @JsonProperty("last_name")
    private String lastName;
    
    @Column(name = "dob")
    @Convert(converter = ZeroDateConverter.class)
    private LocalDate dob;
    
    @Column(name = "profile_url", length = 255)
    @JsonProperty("profile_url")
    private String profileUrl;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    @JsonBackReference("state-profiles")
    private MasterState state;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @JsonBackReference("district-profiles")
    private MasterDistrict district;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tahsil_id")
    @JsonBackReference("tahsil-profiles")
    private MasterTahsil tahsil;
    
    @Column(name = "address_line", length = 255)
    @JsonProperty("address_line")
    private String addressLine;
    
    @Column(name = "about", columnDefinition = "TEXT")
    private String about;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_id")
    @JsonBackReference("profession-profiles")
    private MasterProfession profession;
    
    @Column(name = "business_description", columnDefinition = "TEXT")
    @JsonProperty("business_description")
    private String businessDescription;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = true, updatable = false)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    @Convert(converter = ZeroDateTimeConverter.class)
    private LocalDateTime updatedAt;
    
    // Constructors
    public UserProfile() {}
    
    public UserProfile(User user, String firstName, String lastName, Gender gender) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
    // Gender enum
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
