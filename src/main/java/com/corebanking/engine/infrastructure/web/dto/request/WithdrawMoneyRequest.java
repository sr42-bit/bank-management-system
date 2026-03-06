package com.corebanking.engine.infrastructure.web.dto.request;

import java.math.BigDecimal;

public record WithdrawMoneyRequest(
        BigDecimal amount
) {}