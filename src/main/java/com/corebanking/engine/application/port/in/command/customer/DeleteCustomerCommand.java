package com.corebanking.engine.application.port.in.command.customer;

public record DeleteCustomerCommand(
        String customerId
) {}