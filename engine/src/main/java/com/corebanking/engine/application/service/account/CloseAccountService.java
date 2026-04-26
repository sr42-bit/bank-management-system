package com.corebanking.engine.application.service.account;

import com.corebanking.engine.application.port.in.command.account.CloseAccountCommand;
import com.corebanking.engine.application.port.in.result.account.CloseAccountResult;
import com.corebanking.engine.application.port.in.usecase.account.CloseAccountUseCase;
import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.valueobject.AccountId;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Clock;

@Service
public class CloseAccountService implements CloseAccountUseCase {

    private final AccountRepository accountRepository;
    private final Clock clock;

    public CloseAccountService(AccountRepository accountRepository, Clock clock) {
        this.accountRepository = accountRepository;
        this.clock = clock;
    }

    @Override
    public CloseAccountResult close(CloseAccountCommand command) {

        // 1. Convert to ValueObject
        AccountId accountId = AccountId.of(command.accountId());

        // 2. Load
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(
    HttpStatus.BAD_REQUEST,
    "Account balance must be zero before closing"));

        // 3. Domain behavior
        account.close(clock);

        // 4. Persist
        accountRepository.save(account);

        // 5. Map result (UPDATED METHODS)
        return CloseAccountResult.success(
                account.accountId().value(),
                account.status()
        );
    }
}