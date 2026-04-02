package com.corebanking.engine.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.TransactionJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringTransactionJpaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

private final SpringTransactionJpaRepository transactionRepository;

public TransactionController(SpringTransactionJpaRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
}

// Get all transactions (Admin view)
@GetMapping
public ResponseEntity<List<TransactionJpaEntity>> getAllTransactions() {

    List<TransactionJpaEntity> transactions = transactionRepository.findAll();

    return ResponseEntity.ok(transactions);
}

// Get transactions for a specific account
@GetMapping("/account/{accountId}")
public ResponseEntity<List<TransactionJpaEntity>> getAccountTransactions(
        @PathVariable String accountId) {

    List<TransactionJpaEntity> transactions =
            transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);

    return ResponseEntity.ok(transactions);
}

}
