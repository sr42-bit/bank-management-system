package com.backend_java.vijaymallay.application.ports.out;

import java.util.Optional;

import com.backend_java.vijaymallay.domain.model.Customer;

import java.util.List;

/**
 * OUTBOUND PORT — abstraction for persistence
 */
public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(String customerId);
    List<Customer> findAll(int page, int size);

    boolean existsById(String customerId);
    void deleteById(String customerId);
}
