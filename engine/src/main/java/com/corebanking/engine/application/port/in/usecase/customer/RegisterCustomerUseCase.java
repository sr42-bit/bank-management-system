package com.corebanking.engine.application.port.in.usecase.customer;

import com.corebanking.engine.application.port.in.command.customer.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.customer.RegisterCustomerResult;

public interface RegisterCustomerUseCase {
    RegisterCustomerResult registerCustomer(RegisterCustomerCommand command);
}

