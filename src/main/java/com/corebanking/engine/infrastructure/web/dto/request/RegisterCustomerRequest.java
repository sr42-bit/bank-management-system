package com.corebanking.engine.infrastructure.web.dto.request;

import java.time.LocalDate;
import com.corebanking.engine.domain.model.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record RegisterCustomerRequest(
        @NotBlank(message = "First name is required")
        String firstName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Phone is required")
        String phone,
        @NotNull(message = "Gender is required")
        Gender gender,
        @NotNull(message = "Date of birth is required")
        LocalDate dob
) {}
