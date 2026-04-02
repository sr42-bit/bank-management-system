package com.corebanking.engine.application.port.in.command.customer;

import java.time.LocalDate;
import com.corebanking.engine.domain.model.enums.Gender;

public record UpdateCustomerCommand(
        String customerId,
        String firstName,
        String lastName,
        String email,
        String phone,
        Gender gender,
        LocalDate dob
) {}