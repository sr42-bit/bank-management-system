package com.corebanking.engine.application.port.in.result.money;

import com.corebanking.engine.domain.model.aggregate.Account;

public record TransferMoneyResult(
        Account fromAccount,
        Account toAccount
) {}