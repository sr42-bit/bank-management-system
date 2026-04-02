package com.corebanking.engine.application.service.money;

import com.corebanking.engine.application.port.in.command.money.TransferMoneyCommand;
import com.corebanking.engine.application.port.in.result.money.TransferMoneyResult;
import com.corebanking.engine.application.port.in.usecase.money.TransferMoneyUseCase;
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
public class TransferMoneyService implements TransferMoneyUseCase {

private final AccountRepository accountRepository;
private final TransactionRepository transactionRepository;
private final Clock clock;

public TransferMoneyService(AccountRepository accountRepository,
                            TransactionRepository transactionRepository,
                            Clock clock) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.clock = clock;
}

@Override
public TransferMoneyResult transfer(TransferMoneyCommand command) {

    // Find source account
    Account fromAccount = accountRepository
            .findById(AccountId.of(command.fromAccountId()))
            .orElseThrow(() -> new IllegalArgumentException("Source account not found"));

    // Find destination account
    Account toAccount = accountRepository
            .findById(AccountId.of(command.toAccountId()))
            .orElseThrow(() -> new IllegalArgumentException("Destination account not found"));

    // Convert amount
    Balance amount = Balance.of(command.amount());

    // Perform transfer
    fromAccount.withdraw(amount, clock);
    toAccount.deposit(amount, clock);

    // Save accounts
    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);

    // Create transaction ledger
    Transaction transaction = Transaction.create(
            fromAccount.accountId(),
            toAccount.accountId(),
            amount,
            TransactionType.TRANSFER
    );

    transactionRepository.save(transaction);

    // Return result
    return new TransferMoneyResult(fromAccount, toAccount);
}

}
