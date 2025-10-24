package com.backend_java.Vijay_Mallay.application.ports.out;

import com.backend_java.Vijay_Mallay.domain.model.Customer;
import java.util.Optional;
import java.util.List;

/**
 * OUTBOUND PORT — abstraction for persistence
 */
public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(String customerId);
    List<Customer> findAll(int page, int size);
}
