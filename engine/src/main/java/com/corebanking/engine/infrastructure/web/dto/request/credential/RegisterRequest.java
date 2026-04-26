package com.corebanking.engine.infrastructure.web.dto.request.credential;

public record RegisterRequest(
    String email,
    String password,
    String role
) {}