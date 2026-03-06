package com.corebanking.engine.application.port.in.command.customer;

import java.util.Objects;

public record InfoCustomerCommand(
        String customerId
) {

    public InfoCustomerCommand {
        Objects.requireNonNull(customerId, "customerId cannot be null");

        if (customerId.isBlank()) {
            throw new IllegalArgumentException("customerId cannot be blank");
        }
    }
}