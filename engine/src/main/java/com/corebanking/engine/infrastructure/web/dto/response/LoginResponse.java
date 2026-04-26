package com.corebanking.engine.infrastructure.web.dto.response;

public record LoginResponse(
        String token,
        String role,
        String customerId
) {}