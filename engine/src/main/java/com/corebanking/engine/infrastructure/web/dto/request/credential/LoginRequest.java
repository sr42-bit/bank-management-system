package com.corebanking.engine.infrastructure.web.dto.request.credential;

public record LoginRequest(
        String email,
        String password
) {}