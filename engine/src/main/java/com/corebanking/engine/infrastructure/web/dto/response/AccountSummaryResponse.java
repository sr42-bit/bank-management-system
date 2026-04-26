package com.corebanking.engine.infrastructure.web.dto.response;

import java.math.BigDecimal;

public record AccountSummaryResponse(

        String accountId,
        String accountType,
        String status,
        BigDecimal balance

) {}

