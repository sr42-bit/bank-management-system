package com.corebanking.engine.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCustomerJpaRepository
        extends JpaRepository<CustomerJpaEntity, String> {

    boolean existsByEmail(String email);
}
