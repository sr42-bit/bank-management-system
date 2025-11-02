package com.backend_java.vijaymallay.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend_java.vijaymallay.infrastructure.persistence.entity.AccountJpaEntity;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, String> {

    AccountJpaEntity findByAccountNumber(Long accountNumber);
}
