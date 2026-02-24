package com.corebanking.engine.application.service;

import com.corebanking.engine.application.port.in.result.InfoCustomerResult;
import com.corebanking.engine.application.port.in.usecase.InfoCustomerUseCase;
import com.corebanking.engine.application.port.out.customer.InfoCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.CustomerId;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InfoCustomerService implements InfoCustomerUseCase {

    private final InfoCustomerPort infoCustomerPort;

    public InfoCustomerService(InfoCustomerPort infoCustomerPort) {
        this.infoCustomerPort = infoCustomerPort;
    }

   @Override
public InfoCustomerResult getCustomerById(String rawId) {

    Customer customer = infoCustomerPort
            .loadById(CustomerId.of(rawId))
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Customer not found"
            ));

    return new InfoCustomerResult(
            customer.name().firstName() + " " + customer.name().lastName(),
            customer.email().value(),
            customer.phone().value(),
            customer.gender().name(),
            customer.dob().toString(),
            customer.status().name(),
            customer.kycStatus().name()
    );
}
}