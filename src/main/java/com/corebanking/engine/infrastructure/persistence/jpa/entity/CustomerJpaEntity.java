package com.corebanking.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.corebanking.engine.domain.model.enums.Gender;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dob;

    private String status;
    private String kycStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomerJpaEntity() {
    }

    public CustomerJpaEntity(String id, String firstName, String lastName,
                          String email, String phone, Gender gender,
                          LocalDate dob, String status, String kycStatus,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.status = status;
        this.kycStatus = kycStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters/setters 
    public String getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public Gender getGender() {return gender;}
    public LocalDate getDob() {return dob;}
    public String getStatus() {return status;}
    public String getKycStatus() {return kycStatus;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public LocalDateTime getUpdatedAt() {return updatedAt;}
  
}
