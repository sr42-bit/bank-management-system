package com.corebanking.engine.infrastructure.web.dto.request;

import java.math.BigDecimal;

public record OpenAccountRequest(
        String customerId,
        String accountType,
        BigDecimal initialDeposit
) {}
