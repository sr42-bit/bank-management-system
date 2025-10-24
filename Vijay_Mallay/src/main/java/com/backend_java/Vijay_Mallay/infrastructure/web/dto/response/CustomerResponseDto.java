package com.backend_java.Vijay_Mallay.infrastructure.web.dto.response;

import com.backend_java.Vijay_Mallay.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {
    private String customerId;
    private String fullName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Integer age;
    private String email;
    private String phoneNumber;
    private boolean kycVerified;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
}
