package com.corebanking.engine.infrastructure.web.controller;

import com.corebanking.engine.application.service.auth.AuthService;
import com.corebanking.engine.infrastructure.web.dto.request.credential.LoginRequest;
import com.corebanking.engine.infrastructure.web.dto.request.credential.RegisterRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            String token = authService.login(
                    request.email(),
                    request.password()
            );

            String role = authService.extractRole(token);
            String customerId = authService.extractCustomerId(token);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", role,
                    "customerId", customerId != null ? customerId : ""
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", e.getMessage())
            );
        }
    }

    // ================= REGISTER (ONLY CUSTOMER) =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try {
            // 🔥 STRICT BACKEND VALIDATION
            if (request.role() != null && !request.role().equals("ROLE_USER")) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "Only customers can register")
                );
            }

            authService.registerUser(
                    request.email(),
                    request.password()
            );

            return ResponseEntity.ok(
                    Map.of("message", "Customer registered successfully")
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", e.getMessage())
            );
        }
    }

    // ================= CREATE ADMIN =================
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody RegisterRequest request) {

        try {
            authService.createAdmin(
                    request.email(),
                    request.password()
            );

            return ResponseEntity.ok(
                    Map.of("message", "Admin created successfully")
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", e.getMessage())
            );
        }
    }

    // ================= CREATE EMPLOYEE =================
    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@RequestBody RegisterRequest request) {

        try {
            authService.createEmployee(
                    request.email(),
                    request.password()
            );

            return ResponseEntity.ok(
                    Map.of("message", "Employee created successfully")
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", e.getMessage())
            );
        }
    }
}