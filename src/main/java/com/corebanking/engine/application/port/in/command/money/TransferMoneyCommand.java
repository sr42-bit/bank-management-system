package com.corebanking.engine.application.port.in.command.money;

import java.math.BigDecimal;

public record TransferMoneyCommand(
        String fromAccountId,
        String toAccountId,
        BigDecimal amount
) {
    public TransferMoneyCommand {
        if (fromAccountId.equals(toAccountId))
            throw new IllegalArgumentException("Cannot transfer to same account");

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Transfer amount must be positive");
    }
}