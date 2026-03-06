package com.corebanking.engine.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;

import java.util.Optional;

public interface SpringUserJpaRepository
        extends JpaRepository<UserJpaEntity, String> {

    Optional<UserJpaEntity> findByEmail(String email);
}