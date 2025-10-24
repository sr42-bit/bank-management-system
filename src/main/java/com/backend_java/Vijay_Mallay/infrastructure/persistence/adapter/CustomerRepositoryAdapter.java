package com.backend_java.Vijay_Mallay.infrastructure.persistence.adapter;

import com.backend_java.Vijay_Mallay.application.ports.out.CustomerRepository;
import com.backend_java.Vijay_Mallay.domain.model.Customer;
import com.backend_java.Vijay_Mallay.infrastructure.persistence.entity.CustomerJpaEntity;
import com.backend_java.Vijay_Mallay.infrastructure.persistence.repository.CustomerJpaRepository;
import com.backend_java.Vijay_Mallay.infrastructure.web.mapper.CustomerMapper;
import org.springframework.stereotype.Component;
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
