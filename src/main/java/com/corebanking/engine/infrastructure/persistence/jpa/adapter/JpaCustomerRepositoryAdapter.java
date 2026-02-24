package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import com.corebanking.engine.application.port.out.customer.SaveCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringCustomerJpaRepository;
import com.corebanking.engine.infrastructure.persistence.mapper.CustomerJpaMapper;
import org.springframework.stereotype.Component;

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

    @Override
    public void save(Customer customer) {
        CustomerJpaEntity entity = mapper.toEntity(customer);
        jpaRepository.save(entity);
    }
}