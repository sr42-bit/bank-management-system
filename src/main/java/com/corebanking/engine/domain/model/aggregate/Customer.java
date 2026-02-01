package com.corebanking.engine.domain.model.aggregate;

import com.corebanking.engine.domain.model.enums.*;
import com.corebanking.engine.domain.model.valueobject.*;

import java.time.*;
import java.util.*;

public final class Customer {

    private final CustomerId id;
    private final FullName name;
    private final EmailAddress email;
    private final PhoneNo phone;
    private final LocalDate dob;
    private final Gender gender;

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

        if (Period.between(dob, LocalDate.now(clock)).getYears() < 18)
            throw new IllegalArgumentException("Customer must be at least 18 years old");

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
        this.linkedAccounts.addAll(accounts);
    }

    // ================== FACTORIES ==================
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
            status, kycStatus, kycDocument, accounts,
            createdAt, updatedAt);
    }

    // ================== CLOSURE ==================
 
    // ================== INVARIANTS ==================


    // ================== EVENTS ==================
   
    // ================== SAFE GETTERS ==================
    public CustomerId id() { return id; }
    public FullName name() { return name; }
    public EmailAddress email() { return email; }
    public PhoneNo phone() { return phone; }
    public Gender gender() { return gender; }
    public LocalDate dob() { return dob; }
    public CustomerStatus status() { return status; }
    public KycStatus kycStatus() { return kycStatus; }
    public LocalDateTime updatedAt() { return updatedAt; }
    public List<AccountId> linkedAccounts() { return List.copyOf(linkedAccounts); }
    public LocalDateTime createdAt() {return createdAt;}
    public KycDocument kycDocument() {return kycDocument;}
}
