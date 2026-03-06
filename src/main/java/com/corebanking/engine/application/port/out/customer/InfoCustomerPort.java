package com.corebanking.engine.application.port.out.customer;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;

import java.util.List;
import java.util.Optional;

public interface InfoCustomerPort {

    Optional<Customer> loadById(CustomerId customerId);

    List<Customer> loadAll();
}