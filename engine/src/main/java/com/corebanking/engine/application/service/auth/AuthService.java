package com.corebanking.engine.application.service.auth;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;

import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringUserJpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringAccountJpaRepository;

import com.corebanking.engine.infrastructure.security.jwt.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Service
public class AuthService {

    private final SpringUserJpaRepository userRepository;
    private final SpringCustomerJpaRepository customerRepository;
    private final SpringAccountJpaRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(SpringUserJpaRepository userRepository,
                       SpringCustomerJpaRepository customerRepository,
                       SpringAccountJpaRepository accountRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ================= LOGIN =================
    public String login(String email, String rawPassword) {

        String normalizedEmail = email.trim().toLowerCase();

        UserJpaEntity user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("User disabled");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }

    // ================= REGISTER (SET PASSWORD FLOW) =================
    @Transactional
    public void registerUser(String email, String password) {

        String normalizedEmail = email.trim().toLowerCase();

        // ✅ Must exist (created by admin)
        CustomerJpaEntity customer = customerRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("Customer not found. Contact admin."));

        // ✅ Prevent duplicate user
        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new RuntimeException("Already registered. Please login.");
        }

        // ✅ Create USER only
        UserJpaEntity user = new UserJpaEntity(
                UUID.randomUUID().toString(),
                normalizedEmail,
                passwordEncoder.encode(password),
                "ROLE_USER",
                true
        );

        user.setCustomerId(customer.getCustomerId());
        userRepository.save(user);

        // ✅ Create account if not exists
        if (accountRepository.findByCustomerId(customer.getCustomerId()).isEmpty()) {

            AccountJpaEntity account = new AccountJpaEntity(
                    "ACC-" + UUID.randomUUID().toString().substring(0, 8),
                    customer.getCustomerId(),
                    "SAVINGS",
                    BigDecimal.ZERO,
                    "ACTIVE",
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            accountRepository.save(account);
        }
    }

    // ================= CREATE ADMIN =================
    @Transactional
    public void createAdmin(String email, String password) {

        String normalizedEmail = email.trim().toLowerCase();

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new RuntimeException("Admin already exists");
        }

        UserJpaEntity admin = new UserJpaEntity(
                UUID.randomUUID().toString(),
                normalizedEmail,
                passwordEncoder.encode(password),
                "ROLE_ADMIN",
                true
        );

        userRepository.save(admin);
    }

    // ================= CREATE EMPLOYEE =================
    @Transactional
    public void createEmployee(String email, String password) {

        String normalizedEmail = email.trim().toLowerCase();

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new RuntimeException("Employee already exists");
        }

        UserJpaEntity emp = new UserJpaEntity(
                UUID.randomUUID().toString(),
                normalizedEmail,
                passwordEncoder.encode(password),
                "ROLE_EMPLOYEE",
                true
        );

        userRepository.save(emp);
    }

    // ================= EXTRACT ROLE =================
    public String extractRole(String token) {
        return jwtUtil.extractRole(token);
    }

    // ================= EXTRACT CUSTOMER =================
    public String extractCustomerId(String token) {
        return jwtUtil.extractCustomerId(token);
    }
}