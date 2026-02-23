package com.corebanking.engine.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corebanking.engine.application.port.in.command.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.RegisterCustomerResult;
import com.corebanking.engine.application.port.in.usecase.RegisterCustomerUseCase;
import com.corebanking.engine.application.port.in.usecase.InfoCustomerUseCase;
import com.corebanking.engine.infrastructure.web.dto.request.RegisterCustomerRequest;
import com.corebanking.engine.infrastructure.web.dto.response.InfoCustomerResponse;
import com.corebanking.engine.infrastructure.web.dto.response.RegisterCustomerResponse;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
        @PostConstruct
public void init() {
    System.out.println("🔥 CustomerController loaded!");
}

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final InfoCustomerUseCase infoCustomerUseCase;

    public CustomerController(RegisterCustomerUseCase registerCustomerUseCase,
                              InfoCustomerUseCase infoCustomerUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.infoCustomerUseCase = infoCustomerUseCase;
    }

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

        RegisterCustomerResponse response =
                new RegisterCustomerResponse(result.customerId());

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfoCustomerResponse> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok(infoCustomerUseCase.getCustomerById(id));
    }
}
