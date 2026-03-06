package com.corebanking.engine.infrastructure.web.dto.request;

import java.math.BigDecimal;

public record TransferMoneyRequest(
        String fromAccountId,
        String toAccountId,
        BigDecimal amount
) {}