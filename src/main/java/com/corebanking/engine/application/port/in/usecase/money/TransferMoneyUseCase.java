package com.corebanking.engine.application.port.in.usecase.money;

import java.util.List;

import com.corebanking.engine.application.port.in.command.money.TransferMoneyCommand;
import com.corebanking.engine.application.port.in.result.money.TransferMoneyResult;
import com.corebanking.engine.domain.model.aggregate.Transaction;

public interface TransferMoneyUseCase {
    TransferMoneyResult transfer(TransferMoneyCommand command);
    public interface GetAccountTransactionsUseCase {
    List<Transaction> getTransactions(String accountId);
}
}