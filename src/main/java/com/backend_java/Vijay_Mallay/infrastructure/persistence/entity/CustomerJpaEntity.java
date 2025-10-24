package com.backend_java.Vijay_Mallay.infrastructure.persistence.entity;

import com.backend_java.Vijay_Mallay.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String panNumber;
    private String aadharNumber;
    private boolean kycVerified;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
