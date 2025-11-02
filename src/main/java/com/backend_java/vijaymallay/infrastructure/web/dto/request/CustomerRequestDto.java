package com.backend_java.vijaymallay.infrastructure.web.dto.request;

import java.time.LocalDate;

import com.backend_java.vijaymallay.domain.enums.Gender;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO for creating/updating Customer data from API requests
 */
@Getter
@Builder
public class CustomerRequestDto {

    @NotBlank
    @Size(max = 100)
    private final String fullName;

    @NotNull
    private final Gender gender;

    @NotNull
    private final LocalDate dateOfBirth;

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    @Size(max = 15)
    private final String phoneNumber;

    @Size(max = 10)
    private final String panNumber;

    @Size(max = 12)
    private final String aadharNumber;

    @NotBlank
    private final String addressLine1;

    private final String addressLine2;
    @NotBlank
    private final String city;
    @NotBlank
    private final String state;
    @NotBlank
    private final String postalCode;
    @NotBlank
    private final String country;
}
