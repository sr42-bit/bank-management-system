package com.corebanking.engine.infrastructure.persistence.jpa.repository;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringTransactionJpaRepository
        extends JpaRepository<TransactionJpaEntity, String> {

    List<TransactionJpaEntity> findByFromAccountIdOrToAccountId(
            String fromAccountId,
            String toAccountId
    );
}