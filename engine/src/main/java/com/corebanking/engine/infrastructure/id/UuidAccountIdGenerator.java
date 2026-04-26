package com.corebanking.engine.infrastructure.id;

import com.corebanking.engine.application.port.out.account.AccountIdGenerator;
import com.corebanking.engine.domain.model.valueobject.AccountId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidAccountIdGenerator implements AccountIdGenerator {

    @Override
    public AccountId generate() {

        String random = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16)
                .toUpperCase();

        String formattedId = "ACC-" + random;

        return AccountId.of(formattedId);
    }
}