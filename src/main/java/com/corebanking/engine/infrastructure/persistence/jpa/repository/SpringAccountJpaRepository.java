package com.corebanking.engine.infrastructure.persistence.jpa.repository;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringAccountJpaRepository extends JpaRepository<AccountJpaEntity, String> {

}