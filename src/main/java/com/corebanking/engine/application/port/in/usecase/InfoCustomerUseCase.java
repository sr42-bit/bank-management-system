package com.corebanking.engine.application.port.in.usecase;

import com.corebanking.engine.infrastructure.web.dto.response.InfoCustomerResponse;

public interface InfoCustomerUseCase {
    InfoCustomerResponse getCustomerById(String customerId);    
}
