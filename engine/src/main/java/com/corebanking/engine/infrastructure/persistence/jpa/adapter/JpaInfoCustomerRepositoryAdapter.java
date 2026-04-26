package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.corebanking.engine.application.port.out.customer.InfoCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.persistence.mapper.CustomerJpaMapper;

@Component
public class JpaInfoCustomerRepositoryAdapter implements InfoCustomerPort {

    private final SpringCustomerJpaRepository customerJpaRepository;
    private final CustomerJpaMapper mapper;

    public JpaInfoCustomerRepositoryAdapter(
            SpringCustomerJpaRepository customerJpaRepository,
            CustomerJpaMapper mapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.mapper = mapper;
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Customer> loadById(CustomerId customerId) {
        return customerJpaRepository.findById(customerId.value())
                .map(mapper::toDomain);
    }
    @Override
public List<Customer> loadAll() {
    return customerJpaRepository.findAll()
            .stream()
            .map(mapper::toDomain)
            .toList();
}
}