package com.corebanking.engine.application.port.out.customer;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;

import java.util.Optional;

public interface SaveCustomerPort {

    void save(Customer customer);

    Optional<Customer> loadById(CustomerId customerId);

    void delete(Customer customer);

}
