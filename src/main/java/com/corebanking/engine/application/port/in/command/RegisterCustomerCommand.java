package com.corebanking.engine.application.port.in.command;

import java.time.LocalDate;
import com.corebanking.engine.domain.model.enums.Gender;

public record RegisterCustomerCommand(
        String firstName,
        String lastName,
        String email,
        String phone,
        Gender gender,
        LocalDate dob
) {
    public RegisterCustomerCommand {
        // Optional guard (keeps errors close to boundary)
        if (firstName == null || lastName == null || email == null || phone == null || gender == null || dob == null) {
            throw new IllegalArgumentException("RegisterCustomerCommand fields cannot be null");
        }
    }
}