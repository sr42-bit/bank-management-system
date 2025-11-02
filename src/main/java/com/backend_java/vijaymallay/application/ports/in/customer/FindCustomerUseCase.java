package com.backend_java.vijaymallay.application.ports.in.customer;

import java.util.List;
import java.util.Optional;

import com.backend_java.vijaymallay.domain.model.Customer;

/**
 * Input port for querying customer data.
 * Defines use cases related to retrieving customers.
 */

public interface FindCustomerUseCase  {
     /**
     * Finds a customer by its unique ID.
     *
     * @param customerId unique identifier of the customer
     * @return an Optional containing the Customer if found, otherwise empty
     */

    Optional<Customer> findById(String customerId);
    /**
     * Retrieves a paginated list of all customers.
     *
     * @param page the page number to retrieve (0-based)
     * @param size the number of records per page
     * @return a list of customers for the given page
     */
    List<Customer> findAll(int page, int size);
}
