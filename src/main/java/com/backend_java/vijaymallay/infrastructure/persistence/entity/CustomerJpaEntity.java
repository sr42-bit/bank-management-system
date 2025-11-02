package com.backend_java.vijaymallay.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.backend_java.vijaymallay.domain.enums.Gender;

/**
 * JPA ENTITY — represents database table (customers)
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 10)
    private String panNumber;

    @Column(length = 12)
    private String aadharNumber;

    @Column(length = 20)
    private String voterIdNumber;

    @Column(length = 20)
    private String drivingLicenseNumber;

    private boolean kycVerified; // overall KYC status

    // Address fields
    @Column(length = 255)
    private String addressLine1;

    @Column(length = 255)
    private String addressLine2;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 10)
    private String postalCode;

    @Column(length = 50)
    private String country;

    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Convenience method to set audit fields
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
