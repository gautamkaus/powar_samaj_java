package com.powar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email_id;
    
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number should be 10-15 digits")
    private String mobile_no;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password_hash;
    
    @NotBlank(message = "First name is required")
    private String first_name;
    
    @NotBlank(message = "Last name is required")
    private String last_name;
    
    private String middle_name;
    private String dob;
    private String gender;
    private Long state_id;
    private Long district_id;
    private Long tahsil_id;
    private String address_line;
    private String about;
    private Long profession_id;
    private String business_description;
    
    // Default constructor
    public RegisterRequest() {}
    
    // Constructor with parameters
    public RegisterRequest(String email_id, String mobile_no, String password_hash, String first_name, String last_name) {
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.password_hash = password_hash;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    
    // Getters and Setters
    public String getEmail_id() {
        return email_id;
    }
    
    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
    
    public String getMobile_no() {
        return mobile_no;
    }
    
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
    
    public String getPassword_hash() {
        return password_hash;
    }
    
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
    
    public String getFirst_name() {
        return first_name;
    }
    
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    
    public String getLast_name() {
        return last_name;
    }
    
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getMiddle_name() {
        return middle_name;
    }
    
    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }
    
    public String getDob() {
        return dob;
    }
    
    public void setDob(String dob) {
        this.dob = dob;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public Long getState_id() {
        return state_id;
    }
    
    public void setState_id(Long state_id) {
        this.state_id = state_id;
    }
    
    public Long getDistrict_id() {
        return district_id;
    }
    
    public void setDistrict_id(Long district_id) {
        this.district_id = district_id;
    }
    
    public Long getTahsil_id() {
        return tahsil_id;
    }
    
    public void setTahsil_id(Long tahsil_id) {
        this.tahsil_id = tahsil_id;
    }
    
    public String getAddress_line() {
        return address_line;
    }
    
    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }
    
    public String getAbout() {
        return about;
    }
    
    public void setAbout(String about) {
        this.about = about;
    }
    
    public Long getProfession_id() {
        return profession_id;
    }
    
    public void setProfession_id(Long profession_id) {
        this.profession_id = profession_id;
    }
    
    public String getBusiness_description() {
        return business_description;
    }
    
    public void setBusiness_description(String business_description) {
        this.business_description = business_description;
    }
    
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email_id='" + email_id + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", password_hash='[PROTECTED]'" +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", state_id=" + state_id +
                ", district_id=" + district_id +
                ", tahsil_id=" + tahsil_id +
                ", address_line='" + address_line + '\'' +
                ", about='" + about + '\'' +
                ", profession_id=" + profession_id +
                ", business_description='" + business_description + '\'' +
                '}';
    }
}
