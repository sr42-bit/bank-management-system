package com.backend_java.vijaymallay.domain.model;

import java.time.LocalDateTime;
import java.util.Currency;

import com.backend_java.vijaymallay.domain.enums.AccountType;
import com.backend_java.vijaymallay.domain.valueobject.Money;

// Lombok imports
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Account — Core domain model representing a bank account
 * Contains account details, balances, limits, currency, status, verification, and audit information
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    // Account identifiers
   
    private String accountId;        // Unique account UUID
    private Long accountNumber;      // Numeric account number
    private String customerId;       // Associated customer ID
    private AccountType accountType; // Savings, Current, etc.

    // Currency and Money fields

    private Currency currency;       // Account currency (INR, USD, etc.)
    private Money balance;           // Current balance
    private Money availableBalance;  // Balance available for withdrawal
    private Money pendingBalance;    // Pending transactions

    // Limits (monetary constraints)

    private Money dailyWithdrawalLimit; // Maximum daily withdrawal
    private Money transferLimit;        // Maximum transfer per transaction/day
    private Money overdraftLimit;       // Allowed overdraft

    // Status and verification

    private String status;         // Active, Dormant, Closed
    private String kycStatus;      // KYC Verified / Pending / Rejected
    private String productId;      // Linked banking product ID
    private String branchId;       // Branch handling the account

    // Audit / tracking

    private String createdBy;      // User/employee who created the account
    private LocalDateTime createdAt; // Timestamp of account creation
    private String updatedBy;      // User/employee who last updated
    private LocalDateTime updatedAt; // Timestamp of last update
    private Long version;          // Version for optimistic locking 
    
    // Convenience methods (optional)
   
    /**
     * Deposit money into account
     * @param amount Money object to deposit
     */
    public void deposit(Money amount) {
        if (!amount.getCurrency().equals(this.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        this.balance = this.balance.add(amount);
        this.availableBalance = this.availableBalance.add(amount);
    }

    /**
     * Withdraw money from account
     * @param amount Money object to withdraw
     */
    public void withdraw(Money amount) {
        if (!amount.getCurrency().equals(this.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        if (this.availableBalance.getAmount().compareTo(amount.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
        this.availableBalance = this.availableBalance.subtract(amount);
    }
}
