package com.corebanking.engine.application.service.account;

import com.corebanking.engine.application.port.in.command.account.OpenAccountCommand;
import com.corebanking.engine.application.port.in.result.account.OpenAccountResult;
import com.corebanking.engine.application.port.in.usecase.account.OpenAccountUseCase;
import com.corebanking.engine.application.port.out.account.AccountIdGenerator;
import com.corebanking.engine.application.port.out.account.AccountNoGenerator;
import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.domain.model.valueobject.AccountNo;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.Balance;
import org.springframework.stereotype.Service;
import java.time.Clock;

@Service
public class OpenAccountService implements OpenAccountUseCase {

    private final AccountIdGenerator accountIdGenerator;
    private final AccountRepository accountRepository;
    private final AccountNoGenerator accountNoGenerator;
    private final Clock clock;

    public OpenAccountService(
            AccountIdGenerator accountIdGenerator,
            AccountRepository accountRepository,
            AccountNoGenerator accountNoGenerator,
            Clock clock
    ) {
        this.accountIdGenerator = accountIdGenerator;
        this.accountRepository = accountRepository;
        this.accountNoGenerator = accountNoGenerator;
        this.clock = clock;
    }

    @Override
    public OpenAccountResult open(OpenAccountCommand command) {

        AccountId accountId = accountIdGenerator.generate();
        AccountNo accountNo = accountNoGenerator.generate();
        Balance initialBalance = Balance.of(command.initialDeposit());

        Account account = Account.open(
                accountId,
                CustomerId.of(command.customerId()),
                accountNo,
                command.accountType(),
                initialBalance,
                clock
        );

        accountRepository.save(account);

        return new OpenAccountResult(accountId.value());
    }
}
