package com.corebanking.engine.application.port.in;

public interface RegisterCustomerUseCase {
    RegisterCustomerResult register(RegisterCustomerCommand command);
}
