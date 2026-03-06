package com.corebanking.engine.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corebanking.engine.application.port.in.command.customer.InfoCustomerCommand;
import com.corebanking.engine.application.port.in.command.customer.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.InfoCustomerResult;
import com.corebanking.engine.application.port.in.result.RegisterCustomerResult;
import com.corebanking.engine.application.port.in.usecase.customer.InfoCustomerUseCase;
import com.corebanking.engine.application.port.in.usecase.customer.RegisterCustomerUseCase;
import com.corebanking.engine.infrastructure.web.dto.request.RegisterCustomerRequest;
import com.corebanking.engine.infrastructure.web.dto.response.InfoCustomerResponse;
import com.corebanking.engine.infrastructure.web.dto.response.RegisterCustomerResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final InfoCustomerUseCase infoCustomerUseCase;

    public CustomerController(
            RegisterCustomerUseCase registerCustomerUseCase,
            InfoCustomerUseCase infoCustomerUseCase
    ) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.infoCustomerUseCase = infoCustomerUseCase;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<RegisterCustomerResponse> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request) {

        RegisterCustomerCommand command =
                new RegisterCustomerCommand(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.phone(),
                        request.gender(),
                        request.dob()
                );

        RegisterCustomerResult result =
                registerCustomerUseCase.registerCustomer(command);

        return ResponseEntity.status(201)
                .body(new RegisterCustomerResponse(result.customerId()));
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<InfoCustomerResponse> getCustomer(@PathVariable String id) {

        InfoCustomerCommand command = new InfoCustomerCommand(id);

        InfoCustomerResult result = infoCustomerUseCase.getCustomerById(command);

        InfoCustomerResponse response = new InfoCustomerResponse(
                result.customerId(),
                result.name(),
                result.email(),
                result.phone(),
                result.gender(),
                result.dob(),
                result.status(),
                result.kycStatus()
        );

        return ResponseEntity.ok(response);
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<InfoCustomerResponse>> getAllCustomers() {

        List<InfoCustomerResult> results = infoCustomerUseCase.getAllCustomers();

        List<InfoCustomerResponse> responses = results.stream()
                .map(result -> new InfoCustomerResponse(
                        result.customerId(),
                        result.name(),
                        result.email(),
                        result.phone(),
                        result.gender(),
                        result.dob(),
                        result.status(),
                        result.kycStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody RegisterCustomerRequest request) {

        RegisterCustomerCommand command =
                new RegisterCustomerCommand(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.phone(),
                        request.gender(),
                        request.dob()
                );

        registerCustomerUseCase.updateCustomer(id, command);

        return ResponseEntity.ok().build();
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {

        registerCustomerUseCase.deleteCustomer(id);

        return ResponseEntity.ok().build();
    }
}
