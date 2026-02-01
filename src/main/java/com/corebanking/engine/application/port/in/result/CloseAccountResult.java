package com.corebanking.engine.application.port.in.result;
 
import com.corebanking.engine.domain.model.enums.AccountStatus;


public record CloseAccountResult(
    String accountId,
    AccountStatus status
) 
{
    public static CloseAccountResult success(String account, AccountStatus status) {
        return new CloseAccountResult(account, status);
    }
}
