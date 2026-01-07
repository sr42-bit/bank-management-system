package com.corebanking.engine.infrastructure.id;

import com.corebanking.engine.application.port.out.CustomerIdGenerator;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidCustomerIdGenerator implements CustomerIdGenerator {

    @Override
    public CustomerId generate() {
        return CustomerId.of("CUST-" + UUID.randomUUID());
    }
}
