package com.corebanking.engine.infrastructure.persistence.jpa;

import org.springframework.stereotype.Repository;

import com.corebanking.engine.application.exception.CustomerNotFoundException;
import com.corebanking.engine.application.port.out.CustomerRepository;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final SpringCustomerJpaRepository jpaRepo;
    private final CustomerJpaMapper mapper;

    public CustomerRepositoryImpl(SpringCustomerJpaRepository jpaRepo,
                                  CustomerJpaMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public void save(Customer customer) {
        CustomerJpaEntity entity = mapper.toEntity(customer);
        if (entity != null) {
            jpaRepo.save(entity);
        }
    }

    @Override
    public Customer load(CustomerId id) {
        String idValue = id.value();
        if (idValue == null) {
            throw new CustomerNotFoundException(id);
        }
        return jpaRepo.findById(idValue)
                .map(mapper::toDomain)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return jpaRepo.existsByEmail(email.value());
    }
}
