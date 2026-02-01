package com.corebanking.engine.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corebanking.engine.application.port.in.command.RegisterCustomerCommand;
import com.corebanking.engine.application.port.in.result.RegisterCustomerResult;
import com.corebanking.engine.application.port.in.usecase.RegisterCustomerUseCase;
import com.corebanking.engine.infrastructure.web.dto.RegisterCustomerRequest;
import org.springframework.web.bind.annotation.RequestBody;

 @RestController
@RequestMapping("/customers")
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;

    public CustomerController(RegisterCustomerUseCase registerCustomerUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterCustomerResult> registerCustomer(
            @RequestBody RegisterCustomerRequest request) {

        // Map Web DTO → Application Command
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

        return ResponseEntity.ok(result);
    }
}


