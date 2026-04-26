package com.corebanking.engine.application.port.out.account;

import com.corebanking.engine.domain.model.valueobject.AccountId;
public interface AccountIdGenerator {
    AccountId generate();
}
