package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import com.corebanking.engine.application.port.out.customer.SaveCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.persistence.mapper.CustomerJpaMapper;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaCustomerRepositoryAdapter implements SaveCustomerPort {

    private final SpringCustomerJpaRepository jpaRepository;
    private final CustomerJpaMapper mapper;

    public JpaCustomerRepositoryAdapter(
            SpringCustomerJpaRepository jpaRepository,
            CustomerJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    // ---------------- SAVE ----------------
    @Override
    public void save(Customer customer) {

        CustomerJpaEntity entity = mapper.toEntity(customer);

        jpaRepository.save(entity);

    }

    // ---------------- LOAD BY ID ----------------
    @SuppressWarnings("null")
    @Override
    public Optional<Customer> loadById(CustomerId customerId) {

        return jpaRepository
                .findById(customerId.value())
                .map(mapper::toDomain);

    }

    // ---------------- DELETE ----------------
    @SuppressWarnings("null")
    @Override
    public void delete(Customer customer) {

        jpaRepository.deleteById(customer.id().value());

    }
}
