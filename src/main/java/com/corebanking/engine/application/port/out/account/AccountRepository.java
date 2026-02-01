package com.corebanking.engine.application.port.out.account;

import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.valueobject.AccountId;

import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> findById(AccountId accountId);
}
