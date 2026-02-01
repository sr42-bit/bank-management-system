package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import com.corebanking.engine.application.port.out.customer.CustomerRepository;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.persistence.mapper.CustomerEntityMapper;

import org.springframework.stereotype.Component;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepository {

    private final SpringCustomerJpaRepository jpaRepository;
    private final CustomerEntityMapper mapper;

    public JpaCustomerRepositoryAdapter(
            SpringCustomerJpaRepository jpaRepository,
            CustomerEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Customer customer) {

        CustomerJpaEntity entity = mapper.toEntity(customer);
        jpaRepository.save(entity);
    }
}


