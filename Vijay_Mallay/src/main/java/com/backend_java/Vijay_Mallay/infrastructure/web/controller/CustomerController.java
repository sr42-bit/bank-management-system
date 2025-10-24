package com.backend_java.Vijay_Mallay.infrastructure.web.controller;

import com.backend_java.Vijay_Mallay.application.services.CreateCustomerService;
import com.backend_java.Vijay_Mallay.domain.model.Customer;
import com.backend_java.Vijay_Mallay.infrastructure.web.dto.request.CustomerRequestDto;
import com.backend_java.Vijay_Mallay.infrastructure.web.dto.response.CustomerResponseDto;
import com.backend_java.Vijay_Mallay.infrastructure.web.mapper.CustomerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * REST CONTROLLER — handles API endpoints for Customer
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CreateCustomerService service;

    public CustomerController(CreateCustomerService service) {
        this.service = service;
    }

    /** POST → create new customer */
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto request) {
        Customer domain = CustomerMapper.toDomain(request);
        Customer saved = service.createCustomer(domain);
        CustomerResponseDto response = CustomerMapper.toResponse(saved);
        return ResponseEntity.created(URI.create("/api/v1/customers/" + saved.getCustomerId()))
                .body(response);
    }

    /** GET → find by ID */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable String id) {
        return service.findById(id)
                .map(CustomerMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET → list paginated customers */
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
}
