package com.corebanking.engine.application.port.in.usecase.customer;

import com.corebanking.engine.application.port.in.command.customer.UpdateCustomerCommand;

public interface UpdateCustomerUseCase {
    void updateCustomer(UpdateCustomerCommand command);
}