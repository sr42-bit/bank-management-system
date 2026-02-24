package com.corebanking.engine.infrastructure.id;

import com.corebanking.engine.application.port.out.account.AccountNoGenerator;
import com.corebanking.engine.domain.model.valueobject.AccountNo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidAccountNoGenerator implements AccountNoGenerator {

    @Override
    public AccountNo generate() {
        return AccountNo.of("ACCT-" + UUID.randomUUID());
    }
}