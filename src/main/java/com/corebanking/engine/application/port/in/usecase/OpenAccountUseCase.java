package com.corebanking.engine.application.port.in.usecase;

import com.corebanking.engine.application.port.in.command.OpenAccountCommand;
import com.corebanking.engine.application.port.in.result.OpenAccountResult;

public interface OpenAccountUseCase {
    OpenAccountResult open(OpenAccountCommand command);
}
