package com.corebanking.engine.infrastructure.config;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringUserJpaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(
            SpringUserJpaRepository userRepo,
            PasswordEncoder encoder) {

        return args -> {

            if (userRepo.findByEmail("admin@gmail.com").isEmpty()) {

                // ✅ ONLY CREATE USER (NO CUSTOMER)
                UserJpaEntity admin = new UserJpaEntity(
                        UUID.randomUUID().toString(),
                        "admin@gmail.com",
                        encoder.encode("admin123"),
                        "ROLE_ADMIN",
                        true
                );

                // 🔥 CRITICAL: admin has NO customer
                admin.setCustomerId(null);

                userRepo.save(admin);

                System.out.println("✅ ADMIN CREATED (NO CUSTOMER)");
            }
        };
    }
}