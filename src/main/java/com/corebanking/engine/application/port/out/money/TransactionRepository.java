package com.corebanking.engine.application.port.out.money;

import com.corebanking.engine.domain.model.aggregate.Transaction;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);
    List<Transaction> findByAccountId(AccountId accountId);
}