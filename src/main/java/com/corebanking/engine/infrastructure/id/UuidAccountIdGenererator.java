package com.corebanking.engine.infrastructure.id;

import com.corebanking.engine.domain.model.valueobject.AccountId;
import org.springframework.stereotype.Component;

@Component
public class UuidAccountIdGenererator implements com.corebanking.engine.application.port.out.account.AccountIdGenerator {
    @Override
    public AccountId generate() {
        return AccountId.of(java.util.UUID.randomUUID().toString());
    }
    
}
