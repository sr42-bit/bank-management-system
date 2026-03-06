package com.corebanking.engine.application.port.in.usecase.customer;

import com.corebanking.engine.application.port.in.command.customer.InfoCustomerCommand;
import com.corebanking.engine.application.port.in.result.InfoCustomerResult;

import java.util.List;

public interface InfoCustomerUseCase {

    InfoCustomerResult getCustomerById(InfoCustomerCommand command);

    List<InfoCustomerResult> getAllCustomers();   // add this
}