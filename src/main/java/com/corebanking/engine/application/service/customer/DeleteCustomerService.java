package com.corebanking.engine.application.service.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corebanking.engine.application.port.in.command.customer.DeleteCustomerCommand;
import com.corebanking.engine.application.port.in.usecase.customer.DeleteCustomerUseCase;
import com.corebanking.engine.application.port.out.customer.SaveCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.exception.DomainValidationException;
import com.corebanking.engine.domain.model.valueobject.CustomerId;

import java.time.Clock;

@Service
@Transactional
public class DeleteCustomerService implements DeleteCustomerUseCase {

    private final SaveCustomerPort saveCustomerPort;
    private final Clock clock;

    public DeleteCustomerService(SaveCustomerPort saveCustomerPort, Clock clock) {
        this.saveCustomerPort = saveCustomerPort;
        this.clock = clock;
    }

    @Override
    public void deleteCustomer(DeleteCustomerCommand command) {

        String customerId = command.customerId();

        Customer customer = saveCustomerPort
                .loadById(CustomerId.of(customerId))
                .orElseThrow(() -> new DomainValidationException("Customer not found"));

        customer.deactivate(clock);

        saveCustomerPort.save(customer);
    }
}