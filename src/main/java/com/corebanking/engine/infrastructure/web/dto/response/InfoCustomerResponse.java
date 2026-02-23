package com.corebanking.engine.infrastructure.web.dto.response;

public record InfoCustomerResponse(
        String name,
        String email,
        String phone,
        String gender,
        String dob,
        String status,
        String kycStatus
) {}