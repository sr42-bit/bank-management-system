package com.corebanking.engine.domain.model.aggregate;

import com.corebanking.engine.domain.model.enums.*;
import com.corebanking.engine.domain.model.event.*;
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
    private final List<DomainEvent> domainEvents = new ArrayList<>();

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

        validateInvariants();
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
                status, kycStatus, kycDocument, accounts, createdAt, updatedAt);
    }

    // ================== KYC FLOW ==================
    public void submitKyc(KycDocument document) {
        ensureActive();

        if (!kycStatus.canSubmit())
            throw new IllegalStateException("KYC cannot be submitted in " + kycStatus);

        this.kycDocument = document;
        this.kycStatus = KycStatus.PENDING_REVIEW;
    }

    public void verifyKyc(DomainTime time) {
        ensureActive();

        if (!kycStatus.canVerify())
            throw new IllegalStateException("KYC cannot be verified in " + kycStatus);

        this.kycStatus = KycStatus.VERIFIED;
        this.updatedAt = LocalDateTime.ofInstant(time.value(), ZoneId.systemDefault());

        raiseEvent(new CustomerKycVerifiedEvent(id, time));
    }

    public void rejectKyc() {
        if (!kycStatus.canReject())
            throw new IllegalStateException("KYC cannot be rejected in " + kycStatus);

        this.kycStatus = KycStatus.REJECTED;
    }

    // ================== ACCOUNT LINK ==================
    public void attachAccount(AccountId accountId, DomainTime time) {
        ensureActive();

        if (kycStatus != KycStatus.VERIFIED)
            throw new IllegalStateException("KYC must be VERIFIED");

        if (linkedAccounts.contains(accountId))
            throw new IllegalStateException("Account already linked");

        linkedAccounts.add(accountId);
        this.updatedAt = LocalDateTime.ofInstant(time.value(), ZoneId.systemDefault());

        raiseEvent(new CustomerAccountLinkedEvent(id, accountId, time));
    }

    // ================== CLOSURE ==================
    public void close(DomainTime time) {
        if (!status.canClose())
            throw new IllegalStateException("Customer cannot be closed in " + status);

        if (!linkedAccounts.isEmpty())
            throw new IllegalStateException("Cannot close customer with linked accounts");

        status = CustomerStatus.INACTIVE;
        this.updatedAt = LocalDateTime.ofInstant(time.value(), ZoneId.systemDefault());

        raiseEvent(new CustomerClosedEvent(id, time));
    }

    // ================== INVARIANTS ==================
    private void validateInvariants() {

        if (Period.between(dob, LocalDate.now()).getYears() < 18)
            throw new IllegalStateException("Invalid customer age");

        if (kycStatus == KycStatus.VERIFIED && kycDocument == null)
            throw new IllegalStateException("Verified KYC must contain documents");
    }

    private void ensureActive() {
        if (status != CustomerStatus.ACTIVE)
            throw new IllegalStateException("Customer is not active");
    }

    // ================== EVENTS ==================
    private void raiseEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> pullEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

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
