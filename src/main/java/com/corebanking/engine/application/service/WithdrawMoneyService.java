package com.corebanking.engine.application.service;

import com.corebanking.engine.application.port.in.command.money.WithdrawMoneyCommand;
import com.corebanking.engine.application.port.in.usecase.money.WithdrawMoneyUseCase;
import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.application.port.out.money.TransactionRepository;

import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.aggregate.Transaction;
import com.corebanking.engine.domain.model.enums.TransactionType;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.domain.model.valueobject.Balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@Transactional
public class WithdrawMoneyService implements WithdrawMoneyUseCase {

private final AccountRepository accountRepository;
private final TransactionRepository transactionRepository;
private final Clock clock;

public WithdrawMoneyService(AccountRepository accountRepository,
                            TransactionRepository transactionRepository,
                            Clock clock) {

    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.clock = clock;
}

@Override
public Account withdraw(WithdrawMoneyCommand command) {

    Account account = accountRepository
            .findById(AccountId.of(command.accountId()))
            .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    Balance amount = Balance.of(command.amount());

    account.withdraw(amount, clock);

    accountRepository.save(account);

    // Create transaction ledger entry
    Transaction transaction = Transaction.create(
            account.accountId(),
            null,
            amount,
            TransactionType.WITHDRAW
    );

    transactionRepository.save(transaction);

    return account;
}

}
