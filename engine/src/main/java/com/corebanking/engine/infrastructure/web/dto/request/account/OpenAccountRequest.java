package com.corebanking.engine.infrastructure.web.dto.request.account;

import java.math.BigDecimal;

public record OpenAccountRequest(
        String customerId,
        String accountType,
        BigDecimal initialDeposit
) {}
