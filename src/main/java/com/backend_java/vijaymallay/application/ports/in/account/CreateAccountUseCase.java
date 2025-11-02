package com.backend_java.vijaymallay.application.ports.in.account;

import com.backend_java.vijaymallay.domain.model.Account;

/**
 * INPUT PORT (Use Case Interface)
 * Defines the operation to create a bank account.
 */
public interface CreateAccountUseCase {

    /**
     * Create a new account after business validations.
     *
     * @param account Domain model containing account details
     * @return Saved account instance
     */
    Account createAccount(Account account);
}
