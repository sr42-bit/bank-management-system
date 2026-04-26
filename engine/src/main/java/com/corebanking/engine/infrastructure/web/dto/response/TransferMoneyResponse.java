package com.corebanking.engine.infrastructure.web.dto.response;

import java.math.BigDecimal;

public record TransferMoneyResponse(
        String fromAccountId,
        BigDecimal fromNewBalance,
        String toAccountId,
        BigDecimal toNewBalance,
        String status
) {}