package com.corebanking.engine.application.port.in.result;

public record InfoCustomerResult(
        String name,
        String email,
        String phone,
        String gender,
        String dob,
        String status,
        String kycStatus
) {}