package com.corebanking.engine.application.service;

import com.corebanking.engine.application.port.in.command.CloseAccountCommand;
import com.corebanking.engine.application.port.in.result.CloseAccountResult;
import com.corebanking.engine.application.port.in.usecase.CloseAccountUseCase;
import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.valueobject.AccountId;

import java.time.Clock;
import org.springframework.stereotype.Service;

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

        // 1. Load
        AccountId accountId = AccountId.of(command.accountId());

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account not found"));

        // 2. Domain rule
        account.close(clock);

        // 3. Persist
        accountRepository.save(account);

        // 4. Map result
        return CloseAccountResult.success(
                account.getAccountId().value(),
                account.getStatus()
        );
    }
}
