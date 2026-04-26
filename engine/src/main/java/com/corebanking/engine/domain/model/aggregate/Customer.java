package com.corebanking.engine.domain.model.aggregate;

import com.corebanking.engine.domain.model.enums.*;
import com.corebanking.engine.domain.model.valueobject.*;

import java.time.*;
import java.util.*;

public final class Customer {

    private final CustomerId id;

    private FullName name;
    private EmailAddress email;
    private PhoneNo phone;
    private LocalDate dob;
    private Gender gender;

    private CustomerStatus status;
    private KycStatus kycStatus;
    private KycDocument kycDocument;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private final List<AccountId> linkedAccounts = new ArrayList<>();

    // ================== CREATION ==================
    private Customer(CustomerId id, FullName name, EmailAddress email,
                     PhoneNo phone, Gender gender, LocalDate dob, Clock clock) {

        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(gender);
        Objects.requireNonNull(dob);
        Objects.requireNonNull(clock);

        if (Period.between(dob, LocalDate.now(clock)).getYears() < 18) {
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;

        this.status = CustomerStatus.ACTIVE;
        this.kycStatus = KycStatus.NOT_STARTED;

        this.createdAt = LocalDateTime.now(clock);
        this.updatedAt = this.createdAt;
    }

    // ================== REHYDRATION ==================
    private Customer(CustomerId id, FullName name, EmailAddress email,
                     PhoneNo phone, Gender gender, LocalDate dob,
                     CustomerStatus status, KycStatus kycStatus,
                     KycDocument kycDocument, List<AccountId> accounts,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {

        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(gender);
        Objects.requireNonNull(dob);
        Objects.requireNonNull(status);
        Objects.requireNonNull(kycStatus);
        Objects.requireNonNull(createdAt);
        Objects.requireNonNull(updatedAt);

        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;

        this.status = status;
        this.kycStatus = kycStatus;
        this.kycDocument = kycDocument;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        if (accounts != null) {
            this.linkedAccounts.addAll(accounts);
        }
    }

    // ================== FACTORY METHODS ==================
    public static Customer register(CustomerId id, FullName name, EmailAddress email,
                                    PhoneNo phone, Gender gender,
                                    LocalDate dob, Clock clock) {

        return new Customer(id, name, email, phone, gender, dob, clock);
    }

    public static Customer rehydrate(CustomerId id, FullName name, EmailAddress email,
                                     PhoneNo phone, Gender gender, LocalDate dob,
                                     CustomerStatus status, KycStatus kycStatus,
                                     KycDocument kycDocument, List<AccountId> accounts,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {

        return new Customer(id, name, email, phone, gender, dob,
                status, kycStatus, kycDocument,
                accounts, createdAt, updatedAt);
    }

    // ================== UPDATE DETAILS ==================
    public void updateDetails(
            FullName name,
            EmailAddress email,
            PhoneNo phone,
            Gender gender,
            LocalDate dob,
            Clock clock
    ) {

        Objects.requireNonNull(name);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phone);
        Objects.requireNonNull(gender);
        Objects.requireNonNull(dob);

        if (Period.between(dob, LocalDate.now(clock)).getYears() < 18) {
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        }

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;

        this.updatedAt = LocalDateTime.now(clock);
    }

    // ================== ACCOUNT LINKING ==================
    public void linkAccount(AccountId accountId) {

        Objects.requireNonNull(accountId);

        if (!linkedAccounts.contains(accountId)) {
            linkedAccounts.add(accountId);
        }

        this.updatedAt = LocalDateTime.now();
    }
    // ================== DEACTIVATE ==================
    public void deactivate(java.time.Clock clock) {
        this.status = CustomerStatus.INACTIVE;
        this.updatedAt = java.time.LocalDateTime.now(clock);
    }
    // ================== GETTERS ==================
    public CustomerId id() { return id; }

    public FullName name() { return name; }

    public EmailAddress email() { return email; }

    public PhoneNo phone() { return phone; }

    public Gender gender() { return gender; }

    public LocalDate dob() { return dob; }

    public CustomerStatus status() { return status; }

    public KycStatus kycStatus() { return kycStatus; }

    public LocalDateTime createdAt() { return createdAt; }

    public LocalDateTime updatedAt() { return updatedAt; }

    public KycDocument kycDocument() { return kycDocument; }

    public List<AccountId> linkedAccounts() {
        return List.copyOf(linkedAccounts);
    }
}
