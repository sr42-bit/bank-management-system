package com.corebanking.engine.application.port.in.command;

import com.corebanking.engine.domain.model.enums.AccountType;

public record OpenAccountCommand(
        String customerId,
        AccountType accountType,
        double initialDeposit
) {}