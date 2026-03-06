package com.corebanking.engine.application.port.in.usecase.money;

import com.corebanking.engine.application.port.in.command.money.DepositMoneyCommand;
import com.corebanking.engine.domain.model.aggregate.Account;

public interface DepositMoneyUseCase {
    Account deposit(DepositMoneyCommand command);
}