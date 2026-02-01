package com.corebanking.engine.infrastructure.persistence.jpa.entity;

import com.corebanking.engine.domain.model.enums.AccountStatus;
import com.corebanking.engine.domain.model.enums.AccountType;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class AccountJpaEntity {

    @Id
    @Column(name = "account_id", nullable = false, updatable = false)
    private String accountId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "account_no", nullable = false, unique = true)
    private String accountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "balance", nullable = false)
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /* ===== JPA ===== */
    protected AccountJpaEntity() {}

    /* ===== CONSTRUCTOR USED BY MAPPER ===== */
    public AccountJpaEntity(
            String accountId,
            String customerId,
            String accountNo,
            AccountType accountType,
            double balance,
            AccountStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /* ===== GETTERS ===== */

    public String getAccountId() { return accountId; }
    public String getCustomerId() { return customerId; }
    public String getAccountNo() { return accountNo; }
    public AccountType getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
