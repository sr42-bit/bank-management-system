package com.corebanking.engine.application.service.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corebanking.engine.application.port.in.command.customer.UpdateCustomerCommand;
import com.corebanking.engine.application.port.in.usecase.customer.UpdateCustomerUseCase;
import com.corebanking.engine.application.port.out.customer.SaveCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.exception.DomainValidationException;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;
import com.corebanking.engine.domain.model.valueobject.FullName;
import com.corebanking.engine.domain.model.valueobject.PhoneNo;

import java.time.Clock;

@Service
@Transactional
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final SaveCustomerPort saveCustomerPort;
    private final Clock clock;

    public UpdateCustomerService(SaveCustomerPort saveCustomerPort, Clock clock) {
        this.saveCustomerPort = saveCustomerPort;
        this.clock = clock;
    }

    @Override
    public void updateCustomer(UpdateCustomerCommand command) {

        Customer customer = saveCustomerPort
                .loadById(CustomerId.of(command.customerId()))
                .orElseThrow(() -> new DomainValidationException("Customer not found"));

        customer.updateDetails(
                FullName.of(command.firstName(), command.lastName()),
                EmailAddress.of(command.email()),
                PhoneNo.of(command.phone()),
                command.gender(),
                command.dob(),
                clock
        );

        saveCustomerPort.save(customer);
    }
}