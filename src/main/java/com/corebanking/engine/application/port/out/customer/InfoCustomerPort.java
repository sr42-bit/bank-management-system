package com.corebanking.engine.application.port.out.customer;

import com.corebanking.engine.domain.model.aggregate.Customer;
import java.util.Optional;

public interface InfoCustomerPort {
     Optional<Customer> loadById(String customerId);
}
