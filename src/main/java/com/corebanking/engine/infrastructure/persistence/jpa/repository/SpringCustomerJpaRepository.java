package com.corebanking.engine.infrastructure.persistence.jpa.repository;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringCustomerJpaRepository extends JpaRepository<CustomerJpaEntity, String> {
@Query("""
SELECT MONTH(c.createdAt), COUNT(c)
FROM CustomerJpaEntity c
GROUP BY MONTH(c.createdAt)
ORDER BY MONTH(c.createdAt)
""")
List<Object[]> getCustomerGrowth();
}
