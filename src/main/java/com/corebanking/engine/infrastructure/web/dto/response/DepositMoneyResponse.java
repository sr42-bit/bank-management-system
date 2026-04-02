package com.corebanking.engine.infrastructure.web.dto.response;

public record DepositMoneyResponse(
    String accountId,
    String balance,   
    String status
) {}