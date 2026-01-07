package com.corebanking.engine.infrastructure.persistence.jpa;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.valueobject.*;

import java.util.stream.Collectors;

public class CustomerJpaMapper {

    public CustomerJpaEntity toEntity(Customer c) {

        CustomerJpaEntity e = new CustomerJpaEntity();

        e.setId(c.id().value());
        e.setFirstName(c.name().firstName());
        e.setLastName(c.name().lastName());
        e.setEmail(c.email().value());
        e.setPhone(c.phone().value());
        e.setGender(c.gender());
        e.setStatus(c.status());
        e.setKycStatus(c.kycStatus());
        e.setDob(c.dob());
        e.setCreatedAt(c.createdAt());
        e.setUpdatedAt(c.updatedAt());

        e.setLinkedAccounts(
                c.linkedAccounts()
                 .stream()
                 .map(AccountId::value)
                 .collect(Collectors.toList())
        );

        if (c.kycDocument() != null) {
            e.setAadhaar(c.kycDocument().aadhaar());
            e.setPan(c.kycDocument().pan());
        }

        return e;
    }

    public Customer toDomain(CustomerJpaEntity e) {

        KycDocument doc = null;
        if (e.getAadhaar() != null && e.getPan() != null) {
            doc = KycDocument.of(e.getAadhaar(), e.getPan());
        }

        return Customer.rehydrate(
                CustomerId.of(e.getId()),
                FullName.of(e.getFirstName(), e.getLastName()),
                EmailAddress.of(e.getEmail()),
                PhoneNo.of(e.getPhone()),
                e.getGender(),
                e.getDob(),
                e.getStatus(),
                e.getKycStatus(),
                doc,
                e.getLinkedAccounts()
                 .stream()
                 .map(AccountId::of)
                 .collect(Collectors.toList()),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
