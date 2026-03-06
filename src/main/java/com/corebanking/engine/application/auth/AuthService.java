package com.corebanking.engine.application.auth;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringUserJpaRepository;
import com.corebanking.engine.infrastructure.security.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final SpringUserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(SpringUserJpaRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 🔐 REGISTER
    public void register(String email, String rawPassword) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        UserJpaEntity user = new UserJpaEntity(
                java.util.UUID.randomUUID().toString(),
                email,
                encodedPassword,
                "ROLE_CUSTOMER",
                true
        );

        userRepository.save(user);
    }

    // 🔑 LOGIN
    public String login(String email, String rawPassword) {

        UserJpaEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("User is disabled");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}