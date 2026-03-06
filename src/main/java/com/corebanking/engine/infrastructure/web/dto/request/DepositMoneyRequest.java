package com.corebanking.engine.infrastructure.web.dto.request;

import java.math.BigDecimal;

public record DepositMoneyRequest(
        BigDecimal amount
) {}