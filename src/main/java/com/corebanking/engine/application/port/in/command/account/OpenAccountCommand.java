package com.corebanking.engine.application.port.in.command.account;

import com.corebanking.engine.domain.model.enums.AccountType;

import java.math.BigDecimal;
import java.util.Objects;

public record OpenAccountCommand(
        String customerId,
        AccountType accountType,
        BigDecimal initialDeposit
) {

    public OpenAccountCommand {
        Objects.requireNonNull(customerId, "customerId cannot be null");
        Objects.requireNonNull(accountType, "accountType cannot be null");
        Objects.requireNonNull(initialDeposit, "initialDeposit cannot be null");

        if (customerId.isBlank()) {
            throw new IllegalArgumentException("customerId cannot be blank");
        }

        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }
    }
}