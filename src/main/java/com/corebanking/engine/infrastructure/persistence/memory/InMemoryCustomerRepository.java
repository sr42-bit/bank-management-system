package com.corebanking.engine.infrastructure.persistence.memory;

import com.corebanking.engine.application.exception.CustomerNotFoundException;
import com.corebanking.engine.application.port.out.CustomerRepository;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<String, Customer> store = new ConcurrentHashMap<>();

    @Override
    public void save(Customer customer) {
        store.put(customer.id().value(), customer);
    }

    @Override
    public Customer load(CustomerId id) {
        Customer customer = store.get(id.value());
        if (customer == null)
            throw new CustomerNotFoundException(id);
        return customer;
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return store.values().stream()
                .anyMatch(c -> c.email().equals(email));
    }
}
