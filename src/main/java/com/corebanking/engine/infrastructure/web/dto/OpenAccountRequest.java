package com.corebanking.engine.infrastructure.web.dto;

public record OpenAccountRequest
(
    String customerId,
    String accountType,
    double initialDeposit
) 
{}
