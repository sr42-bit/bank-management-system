package com.corebanking.engine.infrastructure.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corebanking.engine.application.port.in.command.customer.*;
import com.corebanking.engine.application.port.in.result.customer.*;
import com.corebanking.engine.application.port.in.usecase.customer.*;
import com.corebanking.engine.infrastructure.web.dto.request.customer.RegisterCustomerRequest;
import com.corebanking.engine.infrastructure.web.dto.response.InfoCustomerResponse;
import com.corebanking.engine.infrastructure.web.dto.response.RegisterCustomerResponse;
import com.corebanking.engine.shared.ApiResponse;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final InfoCustomerUseCase infoCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerController(
            RegisterCustomerUseCase registerCustomerUseCase,
            InfoCustomerUseCase infoCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase
    ) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.infoCustomerUseCase = infoCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
    }

    // ================= CREATE =================
    @PostMapping
    public ResponseEntity<ApiResponse<RegisterCustomerResponse>> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request) {

        log.info("API CALL: Register Customer - email={}", request.email());

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

        return ResponseEntity
                .status(201)
                .body(ApiResponse.success(
                        new RegisterCustomerResponse(result.customerId())
                ));
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InfoCustomerResponse>> getCustomer(@PathVariable String id) {

        log.info("API CALL: Get Customer - id={}", id);

        InfoCustomerResult result =
                infoCustomerUseCase.getCustomerById(new InfoCustomerCommand(id));

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

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<ApiResponse<List<InfoCustomerResponse>>> getAllCustomers() {

        log.info("API CALL: Get All Customers");

        List<InfoCustomerResponse> responses =
                infoCustomerUseCase.getAllCustomers()
                        .stream()
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

        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody RegisterCustomerRequest request) {

        log.info("API CALL: Update Customer - id={}", id);

        UpdateCustomerCommand command =
                new UpdateCustomerCommand(
                        id,
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.phone(),
                        request.gender(),
                        request.dob()
                );

        updateCustomerUseCase.updateCustomer(command);

        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully"));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable String id) {

        log.info("API CALL: Delete Customer - id={}", id);

        deleteCustomerUseCase.deleteCustomer(new DeleteCustomerCommand(id));

        return ResponseEntity.ok(ApiResponse.success("Customer deleted successfully"));
    }
}