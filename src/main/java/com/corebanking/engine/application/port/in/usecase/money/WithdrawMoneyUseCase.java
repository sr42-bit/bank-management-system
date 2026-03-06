package com.corebanking.engine.application.port.in.usecase.money;

import com.corebanking.engine.application.port.in.command.money.WithdrawMoneyCommand;
import com.corebanking.engine.domain.model.aggregate.Account;

public interface WithdrawMoneyUseCase {
    Account withdraw(WithdrawMoneyCommand command);
}