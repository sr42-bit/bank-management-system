package com.corebanking.engine.application.port.in.result.money;

import java.math.BigDecimal;

public record DepositMoneyResult(
        String accountId,
        BigDecimal newBalance
) {}