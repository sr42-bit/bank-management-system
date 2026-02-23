package com.corebanking.engine.application.service;

import com.corebanking.engine.application.port.in.usecase.InfoCustomerUseCase;
import com.corebanking.engine.application.port.out.customer.InfoCustomerPort;
import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.corebanking.engine.infrastructure.web.dto.response.InfoCustomerResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InfoCustomerService implements InfoCustomerUseCase {

    private final InfoCustomerPort infoCustomerPort;
    private final CustomerEntityMapper mapper;

    public InfoCustomerService(InfoCustomerPort infoCustomerPort,
                               CustomerEntityMapper mapper) {
        this.infoCustomerPort = infoCustomerPort;
        this.mapper = mapper;
    }

    @Override
    public InfoCustomerResponse getCustomerById(String customerId) {
        Customer customer = infoCustomerPort.loadById(customerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found"
                ));

        return mapper.toResponse(customer);
    }
}