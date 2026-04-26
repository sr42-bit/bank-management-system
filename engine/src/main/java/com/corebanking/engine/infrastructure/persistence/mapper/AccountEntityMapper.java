package com.corebanking.engine.infrastructure.persistence.mapper;

import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.enums.AccountStatus;
import com.corebanking.engine.domain.model.enums.AccountType;
import com.corebanking.engine.domain.model.valueobject.*;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityMapper {

    public AccountJpaEntity toEntity(Account account) {
        return new AccountJpaEntity(
                account.accountId().value(),        // primary key
                account.customerId().value(),
                account.accountType().name(),
                account.balance().amount(),
                account.status().name(),
                account.createdAt(),
                account.updatedAt()
        );
    }

    public Account toDomain(AccountJpaEntity entity) {
        return Account.rehydrate(
                AccountId.of(entity.getAccountId()),
                CustomerId.of(entity.getCustomerId()),
                AccountType.valueOf(entity.getAccountType()),
                Balance.of(entity.getBalance()),
                AccountStatus.valueOf(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}