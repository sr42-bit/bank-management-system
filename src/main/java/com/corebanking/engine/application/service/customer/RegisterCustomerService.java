package com.corebanking.engine.application.service.customer;

import com.corebanking.engine.application.port.in.command.customer.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.customer.RegisterCustomerResult;
import com.corebanking.engine.application.port.in.usecase.customer.RegisterCustomerUseCase;
import com.corebanking.engine.application.port.out.customer.CustomerIdGenerator;
import com.corebanking.engine.application.port.out.customer.SaveCustomerPort;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;
import com.corebanking.engine.domain.model.valueobject.FullName;
import com.corebanking.engine.domain.model.valueobject.PhoneNo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@Transactional
public class RegisterCustomerService implements RegisterCustomerUseCase {

    private final SaveCustomerPort saveCustomerPort;
    private final CustomerIdGenerator customerIdGenerator;
    private final Clock clock;

    public RegisterCustomerService(
            SaveCustomerPort saveCustomerPort,
            CustomerIdGenerator customerIdGenerator,
            Clock clock) {

        this.saveCustomerPort = saveCustomerPort;
        this.customerIdGenerator = customerIdGenerator;
        this.clock = clock;
    }

    @Override
    public RegisterCustomerResult registerCustomer(RegisterCustomerCommand command) {

        CustomerId customerId = customerIdGenerator.generate();

        Customer customer = Customer.register(
                customerId,
                FullName.of(command.firstName(), command.lastName()),
                EmailAddress.of(command.email()),
                PhoneNo.of(command.phone()),
                command.gender(),
                command.dob(),
                clock
        );

        saveCustomerPort.save(customer);

        return new RegisterCustomerResult(customerId.value());
    }
}