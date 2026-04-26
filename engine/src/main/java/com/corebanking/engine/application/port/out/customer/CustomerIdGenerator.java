package com.corebanking.engine.application.port.out.customer;

import com.corebanking.engine.domain.model.valueobject.CustomerId;

public interface CustomerIdGenerator {
    CustomerId generate();
}
