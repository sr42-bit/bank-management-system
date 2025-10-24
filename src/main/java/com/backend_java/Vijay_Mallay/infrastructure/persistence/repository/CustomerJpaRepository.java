//pacakge name
package com.backend_java.Vijay_Mallay.infrastructure.persistence.repository;

//local imports
import com.backend_java.Vijay_Mallay.infrastructure.persistence.entity.CustomerJpaEntity;

// spring data imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SPRING DATA REPOSITORY
 * Auto-implements CRUD SQL logic for JPA entity
 */
@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
}
