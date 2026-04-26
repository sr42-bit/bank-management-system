package com.corebanking.engine.infrastructure.web.dto.request.money;

import java.math.BigDecimal;

public record DepositMoneyRequest(
        BigDecimal amount
) {}