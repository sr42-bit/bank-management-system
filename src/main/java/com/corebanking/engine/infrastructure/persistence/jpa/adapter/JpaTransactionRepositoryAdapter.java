package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import com.corebanking.engine.application.port.out.money.TransactionRepository;
import com.corebanking.engine.domain.model.aggregate.Transaction;
import com.corebanking.engine.domain.model.enums.TransactionStatus;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.domain.model.valueobject.Balance;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.TransactionJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringTransactionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JpaTransactionRepositoryAdapter implements TransactionRepository {

    private final SpringTransactionJpaRepository repository;

    public JpaTransactionRepositoryAdapter(SpringTransactionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Transaction transaction) {

        TransactionJpaEntity entity = new TransactionJpaEntity();

        entity.setTransactionId(transaction.transactionId());

        entity.setFromAccountId(
                transaction.fromAccount() != null
                        ? transaction.fromAccount().value()
                        : null
        );

        entity.setToAccountId(
                transaction.toAccount() != null
                        ? transaction.toAccount().value()
                        : null
        );

        entity.setAmount(transaction.amount().value());

        // ✅ FIX: direct enum (NO String)
        entity.setType(transaction.type());

        // ✅ FIX: REQUIRED FIELD (DB NOT NULL)
        entity.setStatus(TransactionStatus.SUCCESS);

        entity.setCreatedAt(transaction.createdAt());

        repository.save(entity);
    }

    @Override
    public List<Transaction> findByAccountId(AccountId accountId) {

        List<TransactionJpaEntity> entities =
                repository.findByFromAccountIdOrToAccountId(
                        accountId.value(),
                        accountId.value()
                );

        return entities.stream()
                .map(entity -> Transaction.create(
                        entity.getFromAccountId() != null
                                ? AccountId.of(entity.getFromAccountId())
                                : null,

                        entity.getToAccountId() != null
                                ? AccountId.of(entity.getToAccountId())
                                : null,

                        Balance.of(entity.getAmount()),

                        // ✅ FIX: already enum, NO valueOf
                        entity.getType()
                ))
                .toList();
    }
}