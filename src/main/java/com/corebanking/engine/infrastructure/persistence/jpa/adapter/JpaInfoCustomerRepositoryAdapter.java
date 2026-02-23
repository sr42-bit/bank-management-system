package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.corebanking.engine.application.port.out.customer.InfoCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.persistence.mapper.CustomerEntityMapper;

@Component
public class JpaInfoCustomerRepositoryAdapter implements InfoCustomerPort {

    private final SpringCustomerJpaRepository customerJpaRepository;
    private final CustomerEntityMapper mapper;

    public JpaInfoCustomerRepositoryAdapter(SpringCustomerJpaRepository customerJpaRepository,
                                           CustomerEntityMapper mapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Customer> loadById(String customerId) {
        if (customerId == null) {
            return Optional.empty();
        }
        return customerJpaRepository.findById(customerId)
                .map(mapper::toDomain);
    }
}
