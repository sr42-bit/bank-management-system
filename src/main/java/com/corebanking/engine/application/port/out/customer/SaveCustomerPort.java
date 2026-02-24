package com.corebanking.engine.application.port.out.customer;

import com.corebanking.engine.domain.model.aggregate.Customer;

public interface SaveCustomerPort {
    void save(Customer customer);
}