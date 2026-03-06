package com.corebanking.engine.infrastructure.web.dto.response;

import java.math.BigDecimal;

public record DepositMoneyResponse(
        String accountId,
        BigDecimal newBalance,
        String status
) {}