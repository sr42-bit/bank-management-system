package com.corebanking.engine.application.port.in.result.customer;

public record UpdateCustomerResult(
        String customerId,
        String message
) {}