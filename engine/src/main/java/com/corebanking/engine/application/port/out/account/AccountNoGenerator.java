package com.corebanking.engine.application.port.out.account;

import com.corebanking.engine.domain.model.valueobject.AccountNo;
public interface AccountNoGenerator {
    AccountNo generate();
}
