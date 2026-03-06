package com.corebanking.engine.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.corebanking.engine.domain.model.enums.Gender;

@Entity
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "varchar(20)")
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "status")
    private String status;

    @Column(name = "kyc_status")
    private String kycStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected CustomerJpaEntity() {}

    public CustomerJpaEntity(String customerId,
                             String firstName,
                             String lastName,
                             String email,
                             String phone,
                             Gender gender,
                             LocalDate dob,
                             String status,
                             String kycStatus,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {
        this.customerId = customerId;
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

    public String getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Gender getGender() { return gender; }
    public LocalDate getDob() { return dob; }
    public String getStatus() { return status; }
    public String getKycStatus() { return kycStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}