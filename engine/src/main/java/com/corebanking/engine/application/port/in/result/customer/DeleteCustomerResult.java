package com.corebanking.engine.application.port.in.result.customer;

public record DeleteCustomerResult(
        String customerId,
        String message
) {}