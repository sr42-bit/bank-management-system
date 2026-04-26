package com.corebanking.engine.infrastructure.web.controller;

import com.corebanking.engine.application.port.in.usecase.customer.InfoCustomerUseCase;
import com.corebanking.engine.application.port.in.usecase.account.OpenAccountUseCase;

import com.corebanking.engine.application.port.in.command.customer.InfoCustomerCommand;
import com.corebanking.engine.application.port.in.command.account.OpenAccountCommand;

import com.corebanking.engine.application.port.in.result.customer.InfoCustomerResult;
import com.corebanking.engine.application.port.in.result.account.OpenAccountResult;

import com.corebanking.engine.infrastructure.persistence.jpa.entity.AccountJpaEntity;
import com.corebanking.engine.infrastructure.persistence.jpa.repository.SpringAccountJpaRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final InfoCustomerUseCase infoCustomerUseCase;
    private final OpenAccountUseCase openAccountUseCase;
    private final SpringAccountJpaRepository accountRepository;

    public AdminController(
            InfoCustomerUseCase infoCustomerUseCase,
            OpenAccountUseCase openAccountUseCase,
            SpringAccountJpaRepository accountRepository
    ) {
        this.infoCustomerUseCase = infoCustomerUseCase;
        this.openAccountUseCase = openAccountUseCase;
        this.accountRepository = accountRepository;
    }

    // ================= CUSTOMERS =================

    @GetMapping("/customers")
    public ResponseEntity<List<InfoCustomerResult>> getAllCustomers() {
        return ResponseEntity.ok(infoCustomerUseCase.getAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<InfoCustomerResult> getCustomer(@PathVariable String id) {
        InfoCustomerCommand command = new InfoCustomerCommand(id);
        return ResponseEntity.ok(infoCustomerUseCase.getCustomerById(command));
    }

    // ================= ACCOUNTS =================

    // ✅ CREATE ACCOUNT (ADMIN ACTION)
    @PostMapping("/accounts")
    public ResponseEntity<OpenAccountResult> openAccount(
            @RequestBody OpenAccountCommand command) {

        return ResponseEntity.ok(openAccountUseCase.open(command));
    }

    // ✅ GET ALL ACCOUNTS (ADMIN VIEW)
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountJpaEntity>> getAllAccounts() {
        return ResponseEntity.ok(accountRepository.findAll());
    }
}