package com.corebanking.engine.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.corebanking.engine.application.port.in.usecase.account.CloseAccountUseCase;
import com.corebanking.engine.application.port.in.usecase.account.OpenAccountUseCase;
import com.corebanking.engine.application.port.in.result.account.OpenAccountResult;
import com.corebanking.engine.application.port.in.result.money.TransferMoneyResult;
import com.corebanking.engine.application.port.in.command.account.CloseAccountCommand;
import com.corebanking.engine.application.port.in.command.account.OpenAccountCommand;
import com.corebanking.engine.application.port.in.command.money.DepositMoneyCommand;
import com.corebanking.engine.application.port.in.command.money.TransferMoneyCommand;
import com.corebanking.engine.application.port.in.command.money.WithdrawMoneyCommand;
import com.corebanking.engine.domain.model.aggregate.Account;
import com.corebanking.engine.domain.model.enums.AccountType;
import com.corebanking.engine.infrastructure.web.dto.request.account.OpenAccountRequest;
import com.corebanking.engine.infrastructure.web.dto.request.money.DepositMoneyRequest;
import com.corebanking.engine.infrastructure.web.dto.request.money.TransferMoneyRequest;
import com.corebanking.engine.infrastructure.web.dto.request.money.WithdrawMoneyRequest;
import com.corebanking.engine.infrastructure.web.dto.response.DepositMoneyResponse;
import com.corebanking.engine.infrastructure.web.dto.response.GrowthResponse;
import com.corebanking.engine.infrastructure.web.dto.response.TransferMoneyResponse;
import com.corebanking.engine.application.port.in.usecase.money.DepositMoneyUseCase;
import com.corebanking.engine.application.port.in.usecase.money.WithdrawMoneyUseCase;
import com.corebanking.engine.application.service.dashboard.DashboardService;
import com.corebanking.engine.application.port.in.usecase.money.TransferMoneyUseCase;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final OpenAccountUseCase openAccountUseCase;
    private final CloseAccountUseCase closeAccountUseCase;
    private final DepositMoneyUseCase depositMoneyUseCase;
    private final WithdrawMoneyUseCase withdrawMoneyUseCase;
    private final TransferMoneyUseCase transferMoneyUseCase;
    private final DashboardService dashboardService;

    public AccountController(OpenAccountUseCase openAccountUseCase, CloseAccountUseCase closeAccountUseCase, DepositMoneyUseCase depositMoneyUseCase, WithdrawMoneyUseCase withdrawMoneyUseCase, TransferMoneyUseCase transferMoneyUseCase, DashboardService dashboardService) {
        this.closeAccountUseCase = closeAccountUseCase;
        this.openAccountUseCase = openAccountUseCase;
        this.depositMoneyUseCase = depositMoneyUseCase;
        this.withdrawMoneyUseCase = withdrawMoneyUseCase;
        this.transferMoneyUseCase = transferMoneyUseCase;
        this.dashboardService = dashboardService;
    }

   @PostMapping
public ResponseEntity<OpenAccountResult> registerAccount(
        @RequestBody OpenAccountRequest request) {

    AccountType accountType;

    try {
        accountType = AccountType.valueOf(request.accountType().toUpperCase());
    } catch (IllegalArgumentException ex) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid account type"
        );
    }

    OpenAccountCommand command = new OpenAccountCommand(
            request.customerId(),
            accountType,
            request.initialDeposit()
    );

    OpenAccountResult result = openAccountUseCase.open(command);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
}

    @PutMapping("/{accountId}/close")
public ResponseEntity<Void> closeAccount(
        @PathVariable String accountId,
        @RequestParam String customerId) {

    CloseAccountCommand command =
            new CloseAccountCommand(customerId, accountId);

    closeAccountUseCase.close(command);

    return ResponseEntity.noContent().build();
}
@PutMapping("/{accountId}/deposit")
public ResponseEntity<DepositMoneyResponse> deposit(
        @PathVariable String accountId,
        @RequestBody DepositMoneyRequest request) {

    DepositMoneyCommand command =
            new DepositMoneyCommand(accountId, request.amount());

    Account account = depositMoneyUseCase.deposit(command);

    DepositMoneyResponse response =
            new DepositMoneyResponse(
                    account.accountId().value(),
                    account.balance().amount().toString(),
                    account.status().name()
            );

    return ResponseEntity.ok(response);
}
@PutMapping("/{accountId}/withdraw")
public ResponseEntity<DepositMoneyResponse> withdraw(
        @PathVariable String accountId,
        @RequestBody WithdrawMoneyRequest request) {

    WithdrawMoneyCommand command =
            new WithdrawMoneyCommand(accountId, request.amount());

    Account account = withdrawMoneyUseCase.withdraw(command);

    DepositMoneyResponse response =
            new DepositMoneyResponse(
                    account.accountId().value(),
                    account.balance().value().toString(),
                    account.status().name()
            );

    return ResponseEntity.ok(response);
}
@PutMapping("/transfer")
public ResponseEntity<TransferMoneyResponse> transfer(
        @RequestBody TransferMoneyRequest request) {

    TransferMoneyCommand command =
            new TransferMoneyCommand(
                    request.fromAccountId(),
                    request.toAccountId(),
                    request.amount()
            );

    TransferMoneyResult result = transferMoneyUseCase.transfer(command);

    TransferMoneyResponse response =
            new TransferMoneyResponse(
                    result.fromAccount().accountId().value(),
                    result.fromAccount().balance().value(),
                    result.toAccount().accountId().value(),
                    result.toAccount().balance().value(),
                    "SUCCESS"
            );

    return ResponseEntity.ok(response);
}@GetMapping("/account-growth")
public List<GrowthResponse> accountGrowth() {
    return dashboardService.getAccountGrowth(0);
}
@GetMapping
public ResponseEntity<List<AccountJpaEntity>> getAllAccounts() {
    List<AccountJpaEntity> accounts = dashboardService.getAllAccounts();
    return ResponseEntity.ok(accounts);
}

@GetMapping("/{accountId}")
public ResponseEntity<AccountJpaEntity> getAccountById(@PathVariable String accountId) {
    AccountJpaEntity account = dashboardService.getAccountById(accountId);
    return ResponseEntity.ok(account);
}
}
