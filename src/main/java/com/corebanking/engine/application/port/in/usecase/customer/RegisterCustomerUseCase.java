package com.corebanking.engine.application.port.in.usecase.customer;

import com.corebanking.engine.application.port.in.command.customer.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.RegisterCustomerResult;

public interface RegisterCustomerUseCase {

    RegisterCustomerResult registerCustomer(RegisterCustomerCommand command);

    void updateCustomer(String customerId, RegisterCustomerCommand command);

    void deleteCustomer(String customerId);

}

