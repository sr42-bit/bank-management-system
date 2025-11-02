//pacakge name
package com.backend_java.vijaymallay.infrastructure.persistence.repository;

// spring data imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend_java.vijaymallay.infrastructure.persistence.entity.CustomerJpaEntity;

/**
 * SPRING DATA REPOSITORY
 * Auto-implements CRUD SQL logic for JPA entity
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
