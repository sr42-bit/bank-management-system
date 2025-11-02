// package declaration
package com.backend_java.vijaymallay.domain.model;

// lombok imports
import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

// java time imports
import java.time.LocalDate;
import java.time.Period;

// java util imports
import java.util.UUID;

import com.backend_java.vijaymallay.domain.enums.Gender;
import com.backend_java.vijaymallay.domain.valueobject.Address;
import com.backend_java.vijaymallay.domain.valueobject.Kyc;
import com.backend_java.vijaymallay.domain.valueobject.Status;
/**
 * Core domain model representing a Bank Customer in the Vijay Mallay backend system.
 * <p>
 * This class is immutable and encapsulates all customer-related information.
 * Business invariants (like mandatory fields, valid email, etc.) are enforced through validation.
 * </p>
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Customer {

    /** Unique business ID (format: BOB-YYYY-XXXXXXXX) */
    @EqualsAndHashCode.Include
    private final String customerId;

    /** Customer personal details */
    private final String fullName;
    private final Gender gender;
    private final LocalDate dateOfBirth;
    private final String email;
    private final String phoneNo;

    /** Customer verification and address info */
    private final Kyc kyc;
    private final Address address;
    private final Status status;

    /**
     * Creates a new validated {@link Customer} instance using a builder pattern.
     * <p>
     * This method enforces domain validation before object creation.
     * </p>
     *
     * @param fullName  the customer's full name
     * @param gender    the customer's gender
     * @param dateOfBirth customer's date of birth
     * @param email     the customer's email address
     * @param phoneNo   the customer's contact number
     * @param kyc       the customer's KYC details
     * @param address   the customer's address
     * @return a validated Customer instance
     */
    @Builder
    public static Customer create(String fullName,Gender gender,LocalDate dateOfBirth,String email,String phoneNo,Kyc kyc,Address address,Status status) {
        Customer customer = Customer.builder()
                .customerId(generateCustomerId())
                .fullName(fullName)
                .gender(gender)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .phoneNo(phoneNo)
                .kyc(kyc)
                .address(address)
                .status(status)
                .build();

        customer.validate();
        return customer;
    }

    public static Customer update(String customerId,String fullName,Gender gender,LocalDate dateOfBirth,String email,String phoneNo,Kyc kyc,Address address,Status status) {
        Customer customer = Customer.builder()
            .customerId(customerId)
            .fullName(fullName)
            .gender(gender)
            .dateOfBirth(dateOfBirth)
            .email(email)
            .phoneNo(phoneNo)
            .kyc(kyc)
            .address(address)
            .status(status)
            .build();

    customer.validate();
    return customer;
    }
        /**
     * Validates domain invariants.
     * Throws {@link IllegalArgumentException} if any validation fails.
     */
    private void validate() {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (phoneNo == null || phoneNo.length() < 8) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address is required");
        }
        if (kyc == null) {
            throw new IllegalArgumentException("KYC details are required");
        }
    }
    public Customer withCustomerId(String customerId) {
    return new Customer(customerId, this.fullName, this.gender, this.dateOfBirth,
                        this.email, this.phoneNo, this.kyc, this.address, this.status);
}
    /** Calculates the customer's age dynamically. */
    public Integer getAge() {
        return (dateOfBirth == null) ? null :
                Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /** Generates a domain-specific customer ID, e.g. BOB-2025-AB12CD34 */
    private static String generateCustomerId() {
        return "BOB-" + LocalDate.now().getYear() + "-"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
