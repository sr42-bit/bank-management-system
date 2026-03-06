package com.corebanking.engine.infrastructure.web.dto.request;

public record LoginRequest(
        String username,
        String password
) {}