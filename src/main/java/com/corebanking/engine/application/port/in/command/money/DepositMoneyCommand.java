package com.corebanking.engine.application.port.in.command.money;

import java.math.BigDecimal;
import java.util.Objects;

public record DepositMoneyCommand(
        String accountId,
        BigDecimal amount
) {
    public DepositMoneyCommand {
        Objects.requireNonNull(accountId);
        Objects.requireNonNull(amount);

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Deposit must be positive");
    }
}