package com.corebanking.engine.application.service;

import com.corebanking.engine.application.port.in.VerifyKycCommand;
import com.corebanking.engine.application.port.in.VerifyKycUseCase;
import com.corebanking.engine.application.port.out.CustomerRepository;
import com.corebanking.engine.application.port.out.DomainEventPublisher;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.DomainTime;

import java.time.Clock;
import java.time.Instant;

public final class VerifyKycService implements VerifyKycUseCase {

    private final CustomerRepository customerRepository;
    private final DomainEventPublisher eventPublisher;
    private final Clock clock;

    public VerifyKycService(CustomerRepository customerRepository,
                            DomainEventPublisher eventPublisher,
                            Clock clock) {
        this.customerRepository = customerRepository;
        this.eventPublisher = eventPublisher;
        this.clock = clock;
    }

    @Override
    public void verifyKyc(VerifyKycCommand command) {

        CustomerId id = CustomerId.of(command.customerId());

        Customer customer = customerRepository.load(id);

        DomainTime now = DomainTime.of(Instant.now(clock));

        customer.verifyKyc(now);

        customerRepository.save(customer);
        eventPublisher.publish(customer.pullEvents());
    }
}
