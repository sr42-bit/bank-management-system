package com.corebanking.engine.application.port.in.usecase;

import com.corebanking.engine.application.port.in.result.InfoCustomerResult;

public interface InfoCustomerUseCase {
    InfoCustomerResult getCustomerById(String customerId);    
}