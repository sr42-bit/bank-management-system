package com.corebanking.engine.domain.model.aggregate;

import com.corebanking.engine.domain.model.enums.AccountType;
import com.corebanking.engine.domain.model.enums.AccountStatus;
import com.corebanking.engine.domain.model.valueobject.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public class Account {

    private final AccountId accountId;
    private final CustomerId customerId;
    private final AccountNo accountNo;
    private final AccountType accountType;

    private Balance balance;
    private AccountStatus status;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /* ================= CONSTRUCTOR ================= */

    private Account(
            AccountId accountId,
            CustomerId customerId,
            AccountNo accountNo,
            AccountType accountType,
            Balance balance,
            AccountStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.accountId = Objects.requireNonNull(accountId);
        this.customerId = Objects.requireNonNull(customerId);
        this.accountNo = Objects.requireNonNull(accountNo);
        this.accountType = Objects.requireNonNull(accountType);
        this.balance = Objects.requireNonNull(balance);
        this.status = Objects.requireNonNull(status);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /* ================= FACTORY ================= */

    public static Account open(
            AccountId accountId,
            CustomerId customerId,
            AccountNo accountNo,
            AccountType accountType,
            Balance initialBalance,
            Clock clock
    ) {
        Objects.requireNonNull(clock);

        return new Account(
                accountId,
                customerId,
                accountNo,
                accountType,
                initialBalance,
                AccountStatus.ACTIVE,
                LocalDateTime.now(clock),
                LocalDateTime.now(clock)
        );
    }

    /* ================= REHYDRATE ================= */

    public static Account rehydrate(
            AccountId accountId,
            CustomerId customerId,
            AccountNo accountNo,
            AccountType accountType,
            Balance balance,
            AccountStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Account(
                accountId,
                customerId,
                accountNo,
                accountType,
                balance,
                status,
                createdAt,
                updatedAt
        );
    }

    /* ================= BEHAVIOR ================= */

    public void deposit(Balance amount, Clock clock) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(clock);

        if (status != AccountStatus.ACTIVE)
            throw new IllegalStateException("Account is not active");

        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now(clock);
    }

    public void withdraw(Balance amount, Clock clock) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(clock);

        if (status != AccountStatus.ACTIVE)
            throw new IllegalStateException("Account is not active");

        if (balance.isLessThan(amount))
            throw new IllegalStateException("Insufficient balance");

        this.balance = this.balance.subtract(amount);
        this.updatedAt = LocalDateTime.now(clock);
    }

    public void close(Clock clock) {
        if (status == AccountStatus.CLOSED) {
            throw new IllegalStateException("Account is already closed");
        }
        if (!balance.isZero()){
            throw new IllegalStateException("Balance must be zero to close account");
        }
        this.status = AccountStatus.CLOSED;
        this.updatedAt = LocalDateTime.now(clock);
    }
    
    /* ================= GETTERS ================= */

    public AccountId getAccountId() { return accountId; }
    public CustomerId getCustomerId() { return customerId; }
    public AccountNo getAccountNo() { return accountNo; }
    public AccountType getAccountType() { return accountType; }
    public Balance getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
