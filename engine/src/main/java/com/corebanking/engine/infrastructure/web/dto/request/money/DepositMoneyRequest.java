package com.corebanking.engine.infrastructure.web.dto.request.money;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DepositMoneyRequest(
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount
) {}
