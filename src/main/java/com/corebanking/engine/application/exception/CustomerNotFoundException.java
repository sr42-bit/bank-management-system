package com.corebanking.engine.application.exception;

import com.corebanking.engine.domain.model.valueobject.CustomerId;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(CustomerId id) {
        super("Customer not found: " + id.value());
    }
}
