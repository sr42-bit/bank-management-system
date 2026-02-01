package com.corebanking.engine.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;    
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.AccountNo;
import com.corebanking.engine.domain.model.valueobject.Balance;
import  org.springframework.lang.NonNull;

@Component
public class AccountEntityMapper {

    public @NonNull AccountJpaEntity toEntity(Account account) {
        return new AccountJpaEntity(
            account.getAccountId().value(),
            account.getCustomerId().value(),
            account.getAccountNo().value(),
            account.getAccountType(),
            account.getBalance().amount(),   // ✅ FIXED
            account.getStatus(),
            account.getCreatedAt(),
            account.getUpdatedAt()
        );
    }

    public Account toDomain(AccountJpaEntity entity) {
        return Account.rehydrate(
            AccountId.of(entity.getAccountId()),
            CustomerId.of(entity.getCustomerId()),
            AccountNo.of(entity.getAccountNo()),
            entity.getAccountType(),
            Balance.of(entity.getBalance()),  // ✅ FIXED
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}
