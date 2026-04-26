package com.corebanking.engine.application.port.in.usecase.account;

import com.corebanking.engine.application.port.in.command.account.CloseAccountCommand;
import com.corebanking.engine.application.port.in.result.account.CloseAccountResult;

public interface CloseAccountUseCase {
    CloseAccountResult close(CloseAccountCommand command);
}
