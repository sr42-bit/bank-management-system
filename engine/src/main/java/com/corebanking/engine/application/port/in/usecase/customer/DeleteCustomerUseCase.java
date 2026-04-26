package com.corebanking.engine.application.port.in.usecase.customer;

import com.corebanking.engine.application.port.in.command.customer.DeleteCustomerCommand;

public interface DeleteCustomerUseCase {
    void deleteCustomer(DeleteCustomerCommand command);
}