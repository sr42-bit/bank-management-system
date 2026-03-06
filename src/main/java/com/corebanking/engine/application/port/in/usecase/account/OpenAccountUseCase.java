package com.corebanking.engine.application.port.in.usecase.account;

import com.corebanking.engine.application.port.in.command.account.OpenAccountCommand;
import com.corebanking.engine.application.port.in.result.OpenAccountResult;

public interface OpenAccountUseCase {
    OpenAccountResult open(OpenAccountCommand command);
}
