package com.corebanking.engine.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corebanking.engine.application.port.in.RegisterCustomerUseCase;
import com.corebanking.engine.application.port.in.VerifyKycUseCase;
import com.corebanking.engine.application.port.out.CustomerIdGenerator;
import com.corebanking.engine.application.port.out.CustomerRepository;
import com.corebanking.engine.application.port.out.DomainEventPublisher;
import com.corebanking.engine.application.service.RegisterCustomerService;
import com.corebanking.engine.application.service.VerifyKycService;

import java.time.Clock;

@Configuration
public class ApplicationBeans {

    @Bean
    public RegisterCustomerUseCase registerCustomerUseCase(CustomerRepository customerRepository,
                                                           CustomerIdGenerator customerIdGenerator,
                                                           Clock clock) {
        return new RegisterCustomerService(customerRepository, customerIdGenerator, clock);
    }

    @Bean
    public VerifyKycUseCase verifyKycUseCase(CustomerRepository customerRepository,
                                             DomainEventPublisher eventPublisher,
                                             Clock clock) {
        return new VerifyKycService(customerRepository, eventPublisher, clock);
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
