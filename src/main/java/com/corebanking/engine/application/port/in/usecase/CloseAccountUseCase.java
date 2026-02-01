package com.corebanking.engine.application.port.in.usecase;

import com.corebanking.engine.application.port.in.command.CloseAccountCommand;
import com.corebanking.engine.application.port.in.result.CloseAccountResult;

public interface CloseAccountUseCase {
    CloseAccountResult close(CloseAccountCommand command);
}
