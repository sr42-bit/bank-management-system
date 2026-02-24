package com.corebanking.engine.infrastructure.id;

import com.corebanking.engine.application.port.out.account.AccountIdGenerator;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidAccountIdGenerator implements AccountIdGenerator {

    @Override
    public AccountId generate() {
        return AccountId.of(UUID.randomUUID().toString());
    }
}