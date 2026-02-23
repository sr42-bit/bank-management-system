package com.corebanking.engine.infrastructure.web.dto.request;

public record OpenAccountRequest
(
    String customerId,
    String accountType,
    double initialDeposit
) 
{}
