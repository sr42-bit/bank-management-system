package com.backend_java.vijaymallay.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend_java.vijaymallay.application.ports.in.customer.CreateCustomerUseCase;
import com.backend_java.vijaymallay.application.ports.in.customer.DeleteCustomerUseCase;
import com.backend_java.vijaymallay.application.ports.in.customer.FindCustomerUseCase;
import com.backend_java.vijaymallay.application.ports.in.customer.UpdateCustomerUseCase;
import com.backend_java.vijaymallay.application.ports.out.CustomerRepository;
import com.backend_java.vijaymallay.domain.model.Customer;


import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements
        CreateCustomerUseCase,
        FindCustomerUseCase,
        UpdateCustomerUseCase,
        DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ---------------- CREATE ----------------
    @Transactional
    
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // ---------------- READ ----------------
    @Override
    public Optional<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Customer> findAll(int page, int size) {
        return customerRepository.findAll(page, size);
    }

    // ---------------- UPDATE ----------------
    @Transactional
    @Override
    public Customer updateCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    // ---------------- DELETE ----------------
    @Transactional
    @Override
    public void deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }
}
