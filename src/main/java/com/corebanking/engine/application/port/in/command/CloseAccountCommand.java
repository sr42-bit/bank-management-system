package com.corebanking.engine.application.port.in.command;

public record CloseAccountCommand (
    String customerId,
    String accountId
)
{}

