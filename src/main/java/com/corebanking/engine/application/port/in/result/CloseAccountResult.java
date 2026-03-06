package com.corebanking.engine.application.port.in.result;

import com.corebanking.engine.domain.model.enums.AccountStatus;

import java.util.Objects;

public record CloseAccountResult(
        String accountId,
        AccountStatus status
) {

    public CloseAccountResult {
        Objects.requireNonNull(accountId, "accountId cannot be null");
        Objects.requireNonNull(status, "status cannot be null");
    }

    public static CloseAccountResult success(String accountId, AccountStatus status) {
        return new CloseAccountResult(accountId, status);
    }
}