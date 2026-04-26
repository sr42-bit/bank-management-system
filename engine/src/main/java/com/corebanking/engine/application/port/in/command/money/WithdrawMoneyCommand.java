package com.corebanking.engine.application.port.in.command.money;

import java.math.BigDecimal;
import java.util.Objects;

public record WithdrawMoneyCommand(
        String accountId,
        BigDecimal amount
) {
    public WithdrawMoneyCommand {
        Objects.requireNonNull(accountId);
        Objects.requireNonNull(amount);

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Withdrawal must be positive");
    }
}