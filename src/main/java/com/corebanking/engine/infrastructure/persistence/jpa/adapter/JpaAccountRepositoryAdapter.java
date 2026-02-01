package com.corebanking.engine.infrastructure.persistence.jpa.adapter;
import com.corebanking.engine.application.port.out.account.AccountRepository;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.infrastructure.persistence.mapper.AccountEntityMapper;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringAccountJpaRepository;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;
import java.util.Optional;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import org.springframework.stereotype.Component;

@Component
public class JpaAccountRepositoryAdapter implements AccountRepository {
    private final SpringAccountJpaRepository jpaAccountRepository;
    private final AccountEntityMapper mapper;

    public JpaAccountRepositoryAdapter(SpringAccountJpaRepository jpaAccountRepository, AccountEntityMapper mapper) {
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
        return jpaAccountRepository.findById(id)
                .map(mapper::toDomain);
    }
}
