package com.corebanking.engine.domain.model.aggregate;

import com.corebanking.engine.domain.model.enums.AccountStatus;
import com.corebanking.engine.domain.model.enums.AccountType;
import com.corebanking.engine.domain.model.valueobject.*;

import jakarta.transaction.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Account {

    private final AccountId accountId;
    private final CustomerId customerId;
    private final AccountType accountType;

    private Balance balance;
    private AccountStatus status;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /* ================= CONSTRUCTOR ================= */

    private Account(
            AccountId accountId,
            CustomerId customerId,
            AccountType accountType,
            Balance balance,
            AccountStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.accountId = Objects.requireNonNull(accountId);
        this.customerId = Objects.requireNonNull(customerId);
        this.accountType = Objects.requireNonNull(accountType);
        this.balance = Objects.requireNonNull(balance);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    /* ================= FACTORY ================= */

    public static Account open(
            AccountId accountId,
            CustomerId customerId,
            AccountNo accountNo, AccountType accountType,
            Balance initialBalance,
            Clock clock
    ) {
        Objects.requireNonNull(clock);

        LocalDateTime now = LocalDateTime.now(clock);

        return new Account(
                accountId,
                customerId,
                accountType,
                Objects.requireNonNull(initialBalance),
                AccountStatus.ACTIVE,
                now,
                now
        );
    }

    /* ================= REHYDRATE ================= */

    public static Account rehydrate(
            AccountId accountId,
            CustomerId customerId,
            AccountType accountType,
            Balance balance,
            AccountStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Account(
                accountId,
                customerId,
                accountType,
                balance,
                status,
                createdAt,
                updatedAt
        );
    }

    /* ================= BEHAVIOR ================= */
    @Transactional
    public void deposit(Balance amount, Clock clock) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(clock);

        ensureCreditAllowed();

        this.balance = this.balance.add(amount);
        touch(clock);
    }

    public void withdraw(Balance amount, Clock clock) {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(clock);

        ensureDebitAllowed();

        Balance newBalance = this.balance.subtract(amount);

        if (!accountType.isOverdraftAllowed() && newBalance.isNegative()) {
            throw new IllegalStateException("Overdraft not allowed for this account type");
        }

        this.balance = newBalance;
        touch(clock);
    }

    public void close(Clock clock) {
        Objects.requireNonNull(clock);

        if (!balance.isZero()) {
            throw new IllegalStateException("Balance must be zero to close account");
        }

        this.status = AccountStatus.CLOSED;
        touch(clock);
    }

    /* ================= STATE GUARDS ================= */

    private void ensureDebitAllowed() {
        if (status == AccountStatus.CLOSED)
            throw new IllegalStateException("Account is closed");

        if (status == AccountStatus.SUSPENDED)
            throw new IllegalStateException("Account is suspended");

        if (status == AccountStatus.FROZEN)
            throw new IllegalStateException("Account is frozen");
    }

    private void ensureCreditAllowed() {
        if (status == AccountStatus.CLOSED)
            throw new IllegalStateException("Account is closed");

        if (status == AccountStatus.SUSPENDED)
            throw new IllegalStateException("Account is suspended");
    }

    private void touch(Clock clock) {
        this.updatedAt = LocalDateTime.now(clock);
    }

    /* ================= GETTERS ================= */

    public AccountId accountId() { return accountId; }
    public CustomerId customerId() { return customerId; }
    public AccountType accountType() { return accountType; }
    public Balance balance() { return balance; }
    public AccountStatus status() { return status; }
    public LocalDateTime createdAt() { return createdAt; }
    public LocalDateTime updatedAt() { return updatedAt; }
}