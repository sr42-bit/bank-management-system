package com.backend_java.Vijay_Mallay.application.services;

import com.backend_java.Vijay_Mallay.application.ports.out.CustomerRepository;
import com.backend_java.Vijay_Mallay.domain.model.Customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * APPLICATION SERVICE
 * Handles use case logic (e.g., create customer, find by ID)
 */
@Service
public class CreateCustomerService {

    private final CustomerRepository customerRepository;

    public CreateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /** Create new customer with business validation */
    @Transactional
    public Customer createCustomer(Customer customer) {
        // Validation: must have DOB
        if (customer.getDateOfBirth() == null) {
            throw new IllegalArgumentException("Date of Birth is required.");
        }

        // Must be adult
        Integer age = customer.getAge();
        if (age == null || age < 18) {
            throw new IllegalArgumentException("Customer must be at least 18 years old.");
        }

        // Set defaults if not present
        if (customer.getCustomerId() == null)
            customer.setCustomerId(Customer.generateCustomerId());

        if (customer.getCreatedDate() == null)
            customer.setCreatedDate(LocalDateTime.now());

        customer.setLastUpdated(LocalDateTime.now());

        // Save to repository (JPA adapter)
        return customerRepository.save(customer);
    }

    /** Find by domain ID */
    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }
        // Inside CreateCustomerService.java
    public List<Customer> findAll(int page, int size) {
        return customerRepository.findAll(page, size);
    }

}
