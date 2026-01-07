package com.corebanking.engine.application.service;

import com.corebanking.engine.application.port.in.*;
import com.corebanking.engine.application.port.out.*;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.*;

import java.time.Clock;

public final class RegisterCustomerService implements RegisterCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerIdGenerator customerIdGenerator;
    private final Clock clock;

    public RegisterCustomerService(CustomerRepository customerRepository,
                                   CustomerIdGenerator customerIdGenerator,
                                   Clock clock) {
        this.customerRepository = customerRepository;
        this.customerIdGenerator = customerIdGenerator;
        this.clock = clock;
    }

    @Override
    public RegisterCustomerResult register(RegisterCustomerCommand cmd) {

        EmailAddress email = EmailAddress.of(cmd.email());

        if (customerRepository.existsByEmail(email))
            throw new IllegalStateException("Customer with email already exists");

        CustomerId customerId = customerIdGenerator.generate();

        Customer customer = Customer.register(
                customerId,
                FullName.of(cmd.firstName(), cmd.lastName()),
                email,
                PhoneNo.of(cmd.phone()),
                cmd.gender(),
                cmd.dob(),
                clock
        );

        customerRepository.save(customer);

        return new RegisterCustomerResult(customerId.value());
    }
}
