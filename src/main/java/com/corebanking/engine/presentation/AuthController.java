package com.corebanking.engine.presentation;

import com.corebanking.engine.application.auth.AuthService;
import com.corebanking.engine.infrastructure.web.dto.request.LoginRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.username(),
                request.password()
        );

        return Map.of("token", token);
    }
}