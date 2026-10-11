package com.corebanking.engine.infrastructure.config;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringUserJpaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class DataInitializer {
    /**
     * Optional operator-controlled bootstrap for the first administrator.
     * Never create a known default administrator password.
     * Remove bootstrap environment variables after the first successful startup.
     */
    @Bean
    CommandLineRunner init(
            SpringUserJpaRepository userRepo,
            PasswordEncoder encoder,
            @Value("${app.bootstrap-admin.email:}") String adminEmail,
            @Value("${app.bootstrap-admin.password:}") String adminPassword) {
        return args -> {
            if (adminEmail == null || adminEmail.isBlank()
                    || adminPassword == null || adminPassword.isBlank()) {
                return;
            }
            if (adminPassword.length() < 12) {
                throw new IllegalStateException("Bootstrap admin password must be at least 12 characters");
            }
            String normalizedEmail = adminEmail.trim().toLowerCase();
            if (userRepo.findByEmail(normalizedEmail).isPresent()) {
                return;
            }
            UserJpaEntity admin = new UserJpaEntity(
                    UUID.randomUUID().toString(),
                    normalizedEmail,
                    encoder.encode(adminPassword),
                    "ROLE_ADMIN",
                    true
            );
            admin.setCustomerId(null);
            userRepo.save(admin);
        };
    }
}