package com.corebanking.engine.application.port.in.usecase;

import com.corebanking.engine.application.port.in.command.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.RegisterCustomerResult;

public interface RegisterCustomerUseCase {
    RegisterCustomerResult registerCustomer(RegisterCustomerCommand command);
    
}
