package com.corebanking.engine.infrastructure.web.dto.request.customer;

import java.time.LocalDate;
import com.corebanking.engine.domain.model.enums.Gender;

public record UpdateCustomerRequest(
        String firstName,
        String lastName,
        String email,
        String phone,
        Gender gender,
        LocalDate dob
) {}