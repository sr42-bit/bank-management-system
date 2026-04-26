package com.corebanking.engine.infrastructure.id;

import com.corebanking.engine.application.port.out.account.AccountNoGenerator;
import com.corebanking.engine.domain.model.valueobject.AccountNo;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class UuidAccountNoGenerator implements AccountNoGenerator {

    private static final SecureRandom random = new SecureRandom();

    @Override
    public AccountNo generate() {

        long number = 100000000000L + 
                (Math.abs(random.nextLong()) % 900000000000L);

        String accountNo = String.valueOf(number);

        return AccountNo.of(accountNo);
    }
}