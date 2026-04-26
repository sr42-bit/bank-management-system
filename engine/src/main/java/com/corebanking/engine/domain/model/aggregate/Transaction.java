package com.corebanking.engine.domain.model.aggregate;

import com.corebanking.engine.domain.model.enums.TransactionType;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.domain.model.valueobject.Balance;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Transaction {

    private final String transactionId;
    private final AccountId fromAccount;
    private final AccountId toAccount;
    private final Balance amount;
    private final TransactionType type;
    private final LocalDateTime createdAt;

    private Transaction(String transactionId,
                        AccountId fromAccount,
                        AccountId toAccount,
                        Balance amount,
                        TransactionType type,
                        LocalDateTime createdAt) {

        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static Transaction create(AccountId from,
                                     AccountId to,
                                     Balance amount,
                                     TransactionType type) {

        return new Transaction(
                "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 16),
                from,
                to,
                amount,
                type,
                LocalDateTime.now()
        );
    }

    public String transactionId() { return transactionId; }
    public AccountId fromAccount() { return fromAccount; }
    public AccountId toAccount() { return toAccount; }
    public Balance amount() { return amount; }
    public TransactionType type() { return type; }
    public LocalDateTime createdAt() { return createdAt; }
}