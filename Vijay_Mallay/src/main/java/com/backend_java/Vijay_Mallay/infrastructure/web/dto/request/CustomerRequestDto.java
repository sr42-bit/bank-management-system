package com.backend_java.Vijay_Mallay.infrastructure.web.dto.request;

import com.backend_java.Vijay_Mallay.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {

    @NotBlank
    @Size(max = 100)
    private String fullName;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 15)
    private String phoneNumber;

    @Size(max = 10)
    private String panNumber;

    @Size(max = 12)
    private String aadharNumber;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
