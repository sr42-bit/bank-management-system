package com.corebanking.engine.infrastructure.persistence.mapper;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.domain.model.enums.CustomerStatus;
import com.corebanking.engine.domain.model.enums.KycStatus;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;
import com.corebanking.engine.domain.model.valueobject.FullName;
import com.corebanking.engine.domain.model.valueobject.PhoneNo;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaMapper {

    public @NonNull CustomerJpaEntity toEntity(Customer customer) {
        return new CustomerJpaEntity(
                customer.id().value(),
                customer.name().firstName(),
                customer.name().lastName(),
                customer.email().value(),
                customer.phone().value(),
                customer.gender(),
                customer.dob(),
                customer.status() != null ? customer.status().name() : null,
                customer.kycStatus() != null ? customer.kycStatus().name() : null,
                customer.createdAt(),
                customer.updatedAt()
        );
    }

    private CustomerStatus safeStatus(String status) {
        try {
            return status != null ? CustomerStatus.valueOf(status) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private KycStatus safeKycStatus(String kyc) {
        try {
            return kyc != null ? KycStatus.valueOf(kyc) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public Customer toDomain(CustomerJpaEntity entity) {
        return Customer.rehydrate(
                CustomerId.of(entity.getId()),
                FullName.of(entity.getFirstName(), entity.getLastName()),
                EmailAddress.of(entity.getEmail()),
                PhoneNo.of(entity.getPhone()),
                entity.getGender(),
                entity.getDob(),
                safeStatus(entity.getStatus()),
                safeKycStatus(entity.getKycStatus()),
                null,
                null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}