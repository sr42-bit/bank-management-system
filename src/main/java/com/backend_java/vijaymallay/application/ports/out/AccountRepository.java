package com.backend_java.vijaymallay.application.ports.out;

import com.backend_java.vijaymallay.domain.model.Account;

/**
 * OUTBOUND PORT
 * Defines operations to persist Account to the database.
 */
public interface AccountRepository {

    /**
     * Save the account to the database.
     *
     * @param account Account domain model
     * @return Saved account instance
     */
    Account save(Account account);

    /**
     * Find an account by account number.
     *
     * @param accountNumber Numeric account number
     * @return Account instance if found, else null
     */
    Account findByAccountNumber(Long accountNumber);
}
