package com.corebanking.engine.application.port.in.usecase.money;

import com.corebanking.engine.application.port.in.command.money.WithdrawMoneyCommand;
import com.corebanking.engine.application.port.in.result.money.WithdrawMoneyResult;

public interface WithdrawMoneyUseCase {
    WithdrawMoneyResult withdraw(WithdrawMoneyCommand command);
}