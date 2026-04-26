package com.corebanking.engine.infrastructure.persistence.jpa.adapter;

import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringAccountJpaRepository;
import com.corebanking.engine.infrastructure.persistence.mapper.AccountEntityMapper;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class JpaAccountRepositoryAdapter implements AccountRepository {

    private final SpringAccountJpaRepository jpaAccountRepository;
    private final AccountEntityMapper mapper;

    public JpaAccountRepositoryAdapter(
            SpringAccountJpaRepository jpaAccountRepository,
            AccountEntityMapper mapper) {

        this.jpaAccountRepository = jpaAccountRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Account account) {

        AccountJpaEntity entity = mapper.toEntity(account);

        if (entity != null) {
            jpaAccountRepository.save(entity);
        }
    }

    @Override
    public Optional<Account> findById(AccountId accountId) {

        String id = accountId.value();

        if (id == null) {
            return Optional.empty();
        }

        return jpaAccountRepository
                .findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public long countAccounts() {
        return jpaAccountRepository.count();
    }

    @Override
    public long countAccountsByStatus(String status) {
        return jpaAccountRepository.countByStatus(status);
    }

    @Override
    public BigDecimal sumTotalBalance() {

        BigDecimal balance = jpaAccountRepository.sumTotalBalance();

        return balance != null ? balance : BigDecimal.ZERO;
    }
}