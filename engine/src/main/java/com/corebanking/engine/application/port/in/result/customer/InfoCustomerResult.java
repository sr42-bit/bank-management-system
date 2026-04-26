package com.corebanking.engine.application.port.in.result.customer;

public record InfoCustomerResult(
        String customerId,
        String name,
        String email,
        String phone,
        String gender,
        String dob,
        String status,
        String kycStatus
) {}