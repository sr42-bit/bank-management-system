package com.corebanking.engine.application.port.in.result.money;

import java.math.BigDecimal;

public record WithdrawMoneyResult(
        String accountId,
        BigDecimal newBalance
) {}