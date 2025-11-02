package com.backend_java.vijaymallay.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend_java.vijaymallay.application.services.CustomerService;
import com.backend_java.vijaymallay.domain.model.Customer;
import com.backend_java.vijaymallay.infrastructure.web.dto.request.CustomerRequestDto;
import com.backend_java.vijaymallay.infrastructure.web.dto.response.CustomerResponseDto;
import com.backend_java.vijaymallay.infrastructure.web.mapper.CustomerMapper;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * REST CONTROLLER — handles API endpoints for Customer
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // ---------------- CREATE ----------------
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerRequestDto request) {

        // Convert DTO → domain
        Customer customerDomain = CustomerMapper.toDomain(request);

        // Let domain handle ID generation & validation
        Customer savedCustomer = service.createCustomer(customerDomain);

        // Convert domain → response DTO
        CustomerResponseDto response = CustomerMapper.toResponse(savedCustomer);

        return ResponseEntity.created(
                        URI.create("/api/v1/customers/" + savedCustomer.getCustomerId()))
                .body(response);
    }

    // ---------------- READ ----------------
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable String id) {
        return service.findById(id)
                .map(CustomerMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<CustomerResponseDto> list = service.findAll(page, size)
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();

        return ResponseEntity.ok(list);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody CustomerRequestDto request) {

        // Convert DTO → domain
        Customer updatedDomain = CustomerMapper.toDomain(request);

        // Use domain factory to create updated Customer with existing ID
        Customer customerToSave = Customer.update(
                id, // path ID
                updatedDomain.getFullName(),
                updatedDomain.getGender(),
                updatedDomain.getDateOfBirth(),
                updatedDomain.getEmail(),
                updatedDomain.getPhoneNo(),
                updatedDomain.getKyc(),
                updatedDomain.getAddress()
                ,updatedDomain.getStatus()
        );

        // Save via service
        Customer saved = service.updateCustomer(customerToSave);

        return ResponseEntity.ok(CustomerMapper.toResponse(saved));
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}