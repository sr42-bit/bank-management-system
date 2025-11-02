package com.backend_java.vijaymallay.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend_java.vijaymallay.application.ports.in.account.CreateAccountUseCase;
import com.backend_java.vijaymallay.application.ports.out.AccountRepository;
import com.backend_java.vijaymallay.domain.model.Account;
import com.backend_java.vijaymallay.domain.valueobject.Money;

import java.math.BigDecimal;

/**
 * APPLICATION SERVICE
 * Handles the Create Account use case.
 * Validates the input and persists the account using the outbound port.
 */
@Service
public class CreateAccountService implements CreateAccountUseCase {

    private final AccountRepository accountRepository;

    // Constructor injection of outbound port (repository)
    public CreateAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Create a new bank account after performing business validation.
     *
     * @param account Domain model containing account details
     * @return Saved account instance
     * @throws IllegalArgumentException if validation fails
     */
    @Override
    @Transactional
    public Account createAccount(Account account) {
        // -------------------------
        // 1. Validate balance
        // -------------------------
        if (account.getBalance() == null || account.getBalance().getAmount().signum() < 0) {
            throw new IllegalArgumentException("Initial balance cannot be null or negative");
        }

        // -------------------------
        // 2. Validate currency
        // -------------------------
        if (account.getCurrency() == null) {
            throw new IllegalArgumentException("Account currency must be defined");
        }

        // -------------------------
        // 3. Validate unique account number
        // -------------------------
        if (account.getAccountNumber() != null
                && accountRepository.findByAccountNumber(account.getAccountNumber()) != null) {
            throw new IllegalArgumentException("Account number already exists");
        }

        // -------------------------
        // 4. Validate customer ID
        // -------------------------
        if (account.getCustomerId() == null || account.getCustomerId().isEmpty()) {
            throw new IllegalArgumentException("Customer ID must be provided");
        }

        // -------------------------
        // 5. Initialize status
        // -------------------------
        if (account.getStatus() == null) {
            account.setStatus("ACTIVE"); // default account status
        }

        // -------------------------
        // 6. Initialize available balance
        // -------------------------
        if (account.getAvailableBalance() == null) {
            account.setAvailableBalance(account.getBalance());
        }

        // -------------------------
        // 7. Initialize pending balance
        // -------------------------
        if (account.getPendingBalance() == null) {
            account.setPendingBalance(Money.zero(account.getCurrency()));
        }

        // -------------------------
        // 8. Initialize limits if null using factory methods
        // -------------------------
        if (account.getDailyWithdrawalLimit() == null) {
            account.setDailyWithdrawalLimit(
                Money.of(BigDecimal.valueOf(100000), account.getCurrency().getCurrencyCode())
            );
        }
        if (account.getTransferLimit() == null) {
            account.setTransferLimit(
                Money.of(BigDecimal.valueOf(500000), account.getCurrency().getCurrencyCode())
            );
        }
        if (account.getOverdraftLimit() == null) {
            account.setOverdraftLimit(Money.zero(account.getCurrency()));
        }

        // -------------------------
        // 9. Initialize audit fields
        // -------------------------
        if (account.getCreatedAt() == null) {
            account.setCreatedAt(java.time.LocalDateTime.now());
        }
        if (account.getUpdatedAt() == null) {
            account.setUpdatedAt(java.time.LocalDateTime.now());
        }
        if (account.getVersion() == null) {
            account.setVersion(1L);
        }

        // -------------------------
        // 10. Persist account
        // -------------------------
        return accountRepository.save(account);
    }
}
