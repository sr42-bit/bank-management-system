package com.corebanking.engine.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corebanking.engine.application.port.in.usecase.OpenAccountUseCase;
import com.corebanking.engine.application.port.in.result.OpenAccountResult;
import com.corebanking.engine.application.port.in.command.OpenAccountCommand;
import com.corebanking.engine.domain.model.enums.AccountType;
import com.corebanking.engine.infrastructure.web.dto.request.OpenAccountRequest;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final OpenAccountUseCase openAccountUseCase;

    public AccountController(OpenAccountUseCase openAccountUseCase) {
        this.openAccountUseCase = openAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<OpenAccountResult> registerAccount(
            @RequestBody OpenAccountRequest request) {

        OpenAccountCommand command = new OpenAccountCommand(
            request.customerId(),
            AccountType.valueOf(request.accountType().toUpperCase()), // ✅ conversion
            request.initialDeposit()
        );

        OpenAccountResult result = openAccountUseCase.open(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(result); // ✅ return
    }
}
