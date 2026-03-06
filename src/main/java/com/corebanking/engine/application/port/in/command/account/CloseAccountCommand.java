package com.corebanking.engine.application.port.in.command.account;

import java.util.Objects;

public record CloseAccountCommand(
        String customerId,
        String accountId
) {

    public CloseAccountCommand {
        Objects.requireNonNull(customerId, "customerId cannot be null");
        Objects.requireNonNull(accountId, "accountId cannot be null");

        if (customerId.isBlank()) {
            throw new IllegalArgumentException("customerId cannot be blank");
        }

        if (accountId.isBlank()) {
            throw new IllegalArgumentException("accountId cannot be blank");
        }
    }
}