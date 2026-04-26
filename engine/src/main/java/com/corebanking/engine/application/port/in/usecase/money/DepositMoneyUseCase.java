package com.corebanking.engine.application.port.in.usecase.money;

import com.corebanking.engine.application.port.in.command.money.DepositMoneyCommand;
import com.corebanking.engine.application.port.in.result.money.DepositMoneyResult;

public interface DepositMoneyUseCase {
    DepositMoneyResult deposit(DepositMoneyCommand command);
}