package com.corebanking.engine.infrastructure.persistence.mapper;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.infrastructure.web.dto.response.InfoCustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerWebMapper {

    public InfoCustomerResponse toResponse(Customer customer) {

        return new InfoCustomerResponse(
                customer.id().value(),
                customer.name() != null
                        ? customer.name().firstName() + " " + customer.name().lastName()
                        : null,
                customer.email() != null
                        ? customer.email().value()
                        : null,
                customer.phone() != null
                        ? customer.phone().value()
                        : null,
                customer.gender() != null
                        ? customer.gender().name()
                        : null,
                customer.dob() != null
                        ? customer.dob().toString()
                        : null,
                customer.status() != null
                        ? customer.status().name()
                        : null,
                customer.kycStatus() != null
                        ? customer.kycStatus().name()
                        : null
        );
    }
}