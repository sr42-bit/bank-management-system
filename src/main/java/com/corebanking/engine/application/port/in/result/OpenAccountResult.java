package com.corebanking.engine.application.port.in.result;

import java.util.Objects;

public record OpenAccountResult(
        String accountId
) {

    public OpenAccountResult {
        Objects.requireNonNull(accountId, "accountId cannot be null");

        if (accountId.isBlank()) {
            throw new IllegalArgumentException("accountId cannot be blank");
        }
    }

    public static OpenAccountResult success(String accountId) {
        return new OpenAccountResult(accountId);
    }
}