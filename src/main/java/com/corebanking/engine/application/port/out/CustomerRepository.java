package com.corebanking.engine.application.port.out;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;

public interface CustomerRepository {

    void save(Customer customer);

    Customer load(CustomerId id);

    boolean existsByEmail(EmailAddress email);
}
