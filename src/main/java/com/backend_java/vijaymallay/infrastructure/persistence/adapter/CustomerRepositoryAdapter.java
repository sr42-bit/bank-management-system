package com.backend_java.vijaymallay.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import com.backend_java.vijaymallay.application.ports.out.CustomerRepository;
import com.backend_java.vijaymallay.domain.model.Customer;
import com.backend_java.vijaymallay.infrastructure.persistence.entity.CustomerJpaEntity;
import com.backend_java.vijaymallay.infrastructure.persistence.repository.CustomerJpaRepository;
import com.backend_java.vijaymallay.infrastructure.web.mapper.CustomerMapper;

import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

/**
 * ADAPTER — converts between domain model and JPA entity
 */
@Component
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    public CustomerRepositoryAdapter(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = CustomerMapper.toEntity(customer);
        entity.setCreatedAt(entity.getCreatedAt() == null ? java.time.LocalDateTime.now() : entity.getCreatedAt());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        CustomerJpaEntity saved = jpaRepository.save(entity);
        return CustomerMapper.toDomain(saved);
    }
    @Override
    public void deleteById(String customerId) {
        // Parse numeric ID from domain ID like "CUST-123"
        Long dbId = null;
        try {
            dbId = Long.parseLong(customerId.replace("CUST-", ""));
        } catch (Exception ignored) {}

        if (dbId != null) {
            jpaRepository.deleteById(dbId);
        }
    }

    @Override
    public boolean existsById(String customerId) {
        // Parse numeric ID from domain ID like "CUST-123"
        Long dbId = null;
        try {
            dbId = Long.parseLong(customerId.replace("CUST-", ""));
        } catch (Exception ignored) {}

        if (dbId == null) return false;
        return jpaRepository.existsById(dbId);
    }
    
    @Override
    public Optional<Customer> findById(String id) {
        // Parse numeric ID from domain ID like "CUST-123"
        Long dbId = null;
        try {
            dbId = Long.parseLong(id.replace("CUST-", ""));
        } catch (Exception ignored) {}

        if (dbId == null) return Optional.empty();
        return jpaRepository.findById(dbId).map(CustomerMapper::toDomain);
    }

    @Override
    public List<Customer> findAll(int page, int size) {
        return jpaRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(CustomerMapper::toDomain)
                .toList();
    }
}
