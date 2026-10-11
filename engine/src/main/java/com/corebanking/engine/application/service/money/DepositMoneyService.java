package com.corebanking.engine.application.service.money;

import com.corebanking.engine.application.port.in.command.money.DepositMoneyCommand;
import com.corebanking.engine.application.port.in.usecase.money.DepositMoneyUseCase;
import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.enums.TransactionStatus;
import com.corebanking.engine.domain.model.enums.TransactionType;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.domain.model.valueobject.Balance;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.TransactionJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringTransactionJpaRepository;
import com.corebanking.engine.application.port.in.result.money.DepositMoneyResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DepositMoneyService implements DepositMoneyUseCase {
    private final AccountRepository accountRepository;
    private final SpringTransactionJpaRepository transactionRepository;
    private final Clock clock;

    public DepositMoneyService(AccountRepository accountRepository,
                               SpringTransactionJpaRepository transactionRepository,
                               Clock clock) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.clock = clock;
    }

    @Override
    @Transactional
    public DepositMoneyResult deposit(DepositMoneyCommand command) {
        Balance amount = Balance.positiveAmount(command.amount());
        Account account = accountRepository.findById(AccountId.of(command.accountId()))
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.deposit(amount, clock);
        accountRepository.save(account);

        LocalDateTime now = LocalDateTime.now(clock);
        TransactionJpaEntity txn = new TransactionJpaEntity();
        txn.setTransactionId("TXN-" + UUID.randomUUID());
        txn.setFromAccountId(null);
        txn.setToAccountId(command.accountId());
        txn.setAmount(amount.value());
        txn.setType(TransactionType.DEPOSIT);
        txn.setStatus(TransactionStatus.SUCCESS);
        txn.setCreatedAt(now);
        txn.setUpdatedAt(now);
        transactionRepository.save(txn);

        return new DepositMoneyResult(account.accountId().value(), account.balance().value());
    }
}