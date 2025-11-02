package com.backend_java.vijaymallay.infrastructure.web.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.backend_java.vijaymallay.domain.enums.Gender;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO for sending Customer data in API responses
 */
@Getter
@Builder
public class CustomerResponseDto {

    private final String customerId;
    private final String fullName;
    private final Gender gender;
    private final LocalDate dateOfBirth;
    private final Integer age;
    private final String email;
    private final String phoneNumber;
    private final boolean kycVerified;
    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String country;
    private final boolean active;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastUpdated;

}
