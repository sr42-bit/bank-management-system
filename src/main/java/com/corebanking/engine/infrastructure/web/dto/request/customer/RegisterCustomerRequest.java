package com.corebanking.engine.infrastructure.web.dto.request.customer;

import java.time.LocalDate;

import com.corebanking.engine.domain.model.enums.Gender;

import jakarta.validation.constraints.*;

public record RegisterCustomerRequest(

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(
        regexp = "^\\+[1-9]\\d{7,14}$",
        message = "Phone must be in E.164 format (e.g. +919876543210)")
        String phone, 

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dob

) {}