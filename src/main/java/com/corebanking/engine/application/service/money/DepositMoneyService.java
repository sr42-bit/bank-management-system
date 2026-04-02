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

import org.springframework.stereotype.Service;

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
public Account deposit(DepositMoneyCommand command) {

    // Find account
    Account account = accountRepository
            .findById(AccountId.of(command.accountId()))
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    // Convert amount to Balance object
    Balance amount = Balance.of(command.amount());

    // Update account balance
    account.deposit(amount, clock);

    // Save updated account
    accountRepository.save(account);

    // Create transaction record
    TransactionJpaEntity txn = new TransactionJpaEntity();

    txn.setTransactionId("TXN-" + UUID.randomUUID());
    txn.setFromAccountId(command.accountId());
    txn.setToAccountId(null);
    txn.setAmount(command.amount());
    txn.setType(TransactionType.DEPOSIT);
    
    txn.setStatus(TransactionStatus.SUCCESS);
    
    txn.setCreatedAt(LocalDateTime.now());
    txn.setUpdatedAt(LocalDateTime.now()); // (optional but good)
    
    transactionRepository.save(txn);

    return account;
}

}
