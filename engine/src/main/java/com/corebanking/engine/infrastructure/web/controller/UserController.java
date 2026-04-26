package com.corebanking.engine.infrastructure.web.controller;

import com.corebanking.engine.application.port.in.usecase.money.DepositMoneyUseCase;
import com.corebanking.engine.application.port.in.usecase.money.WithdrawMoneyUseCase;

import com.corebanking.engine.application.port.in.command.money.DepositMoneyCommand;
import com.corebanking.engine.application.port.in.command.money.WithdrawMoneyCommand;

import com.corebanking.engine.application.port.in.result.money.DepositMoneyResult;
import com.corebanking.engine.application.port.in.result.money.WithdrawMoneyResult;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.UserJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.TransactionJpaEntity;

import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringAccountJpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringUserJpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringTransactionJpaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final DepositMoneyUseCase depositMoneyUseCase;
    private final WithdrawMoneyUseCase withdrawMoneyUseCase;
    private final SpringAccountJpaRepository accountRepository;
    private final SpringUserJpaRepository userRepository;
    private final SpringTransactionJpaRepository transactionRepository;

    public UserController(
            DepositMoneyUseCase depositMoneyUseCase,
            WithdrawMoneyUseCase withdrawMoneyUseCase,
            SpringAccountJpaRepository accountRepository,
            SpringUserJpaRepository userRepository,
            SpringTransactionJpaRepository transactionRepository
    ) {
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.withdrawMoneyUseCase = withdrawMoneyUseCase;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ================= COMMON METHOD =================
    private String getCustomerId(Authentication authentication) {
        String email = authentication.getName().trim().toLowerCase();

        UserJpaEntity user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String customerId = user.getCustomerId();

        if (customerId == null) {
            throw new RuntimeException("Customer not linked");
        }

        return customerId;
    }

    // ================= ACCOUNTS =================
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountJpaEntity>> getUserAccounts(Authentication authentication) {

        String customerId = getCustomerId(authentication);

        List<AccountJpaEntity> accounts =
                accountRepository.findByCustomerId(customerId);

        return ResponseEntity.ok(accounts);
    }

    // ================= DEPOSIT =================
    @PostMapping("/deposit")
    public ResponseEntity<DepositMoneyResult> deposit(
            @RequestBody DepositMoneyCommand command
    ) {
        return ResponseEntity.ok(depositMoneyUseCase.deposit(command));
    }

    // ================= WITHDRAW =================
    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawMoneyResult> withdraw(
            @RequestBody WithdrawMoneyCommand command
    ) {
        return ResponseEntity.ok(withdrawMoneyUseCase.withdraw(command));
    }

    // ================= TRANSACTIONS (NEW) =================
    @GetMapping("/transactions")
public ResponseEntity<List<TransactionJpaEntity>> getTransactions(Authentication authentication) {

    String customerId = getCustomerId(authentication);

    // ✅ 1. Get all accounts of user
    List<AccountJpaEntity> accounts =
            accountRepository.findByCustomerId(customerId);

    // ✅ 2. Fetch transactions for each account
    List<TransactionJpaEntity> transactions = accounts.stream()
            .flatMap(acc ->
                    transactionRepository
                            .findByFromAccountIdOrToAccountIdOrderByCreatedAtDesc(
                                    acc.getAccountId(),
                                    acc.getAccountId()
                            )
                            .stream()
            )
            .toList();

    return ResponseEntity.ok(transactions);
}
}