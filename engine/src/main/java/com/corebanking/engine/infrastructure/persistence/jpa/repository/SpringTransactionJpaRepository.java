package com.corebanking.engine.infrastructure.persistence.jpa.repository;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringTransactionJpaRepository
        extends JpaRepository<TransactionJpaEntity, String> {

    // ✅ Account transactions (user)
    List<TransactionJpaEntity> findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(
            String fromAccountId,
            String toAccountId
    );

    // ✅ All transactions (admin)
    List<TransactionJpaEntity> findAllByOrderByCreatedAtDesc();
}