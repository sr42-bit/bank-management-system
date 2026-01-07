package com.corebanking.engine.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corebanking.engine.application.port.in.*;
import com.corebanking.engine.infrastructure.web.dto.RegisterCustomerRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final RegisterCustomerUseCase registerUseCase;
    private final VerifyKycUseCase verifyKycUseCase;

    public CustomerController(RegisterCustomerUseCase registerUseCase,
                              VerifyKycUseCase verifyKycUseCase) {
        this.registerUseCase = registerUseCase;
        this.verifyKycUseCase = verifyKycUseCase;
    }

    @PostMapping
    public ResponseEntity<RegisterCustomerResult> register(
            @RequestBody RegisterCustomerRequest r) {

        RegisterCustomerCommand cmd = new RegisterCustomerCommand(
                r.firstName(),
                r.lastName(),
                r.email(),
                r.phone(),
                r.gender(),
                r.dob()
        );

        RegisterCustomerResult result = registerUseCase.register(cmd);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/kyc")
    public ResponseEntity<Void> verifyKyc(@PathVariable String id) {

        verifyKycUseCase.verifyKyc(new VerifyKycCommand(id));
        return ResponseEntity.ok().build();
    }
}
