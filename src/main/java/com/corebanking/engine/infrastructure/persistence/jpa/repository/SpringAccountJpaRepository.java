package com.corebanking.engine.infrastructure.persistence.jpa.repository;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface SpringAccountJpaRepository extends JpaRepository<AccountJpaEntity, String> {

    long countByStatus(String status);

    @Query("SELECT SUM(a.balance) FROM AccountJpaEntity a")
    BigDecimal sumTotalBalance();

    @Query("""
    SELECT MONTH(a.createdAt), COUNT(a)
    FROM AccountJpaEntity a
    GROUP BY MONTH(a.createdAt)
    ORDER BY MONTH(a.createdAt)
    """)
    List<Object[]> getAccountGrowth();
}