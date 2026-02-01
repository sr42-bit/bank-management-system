package com.corebanking.engine.infrastructure.persistence.mapper;

import com.corebanking.engine.domain.model.aggregate.Customer;
import com.corebanking.engine.infrastructure.persistence.jpa.entity.CustomerJpaEntity;
import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.FullName;
import com.corebanking.engine.domain.model.valueobject.EmailAddress;
import com.corebanking.engine.domain.model.valueobject.PhoneNo;
import com.corebanking.engine.domain.model.enums.CustomerStatus;
import com.corebanking.engine.domain.model.enums.KycStatus;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class CustomerEntityMapper {

    public @NonNull CustomerJpaEntity toEntity(Customer customer) {

        return new CustomerJpaEntity(
                customer.id().value(),
                customer.name().firstName(),
                customer.name().lastName(),
                customer.email().value(),
                customer.phone().value(),
                customer.gender(),
                customer.dob(),
                customer.status().name(),
                customer.kycStatus().name(),
                customer.createdAt(),
                customer.updatedAt()
        );
    }
    // You will use this later when we read from DB
    public Customer toDomain(CustomerJpaEntity entity) {



        return Customer.rehydrate(
                CustomerId.of(entity.getId()),
                FullName.of(entity.getFirstName(), entity.getLastName()),
                EmailAddress.of(entity.getEmail()),
                PhoneNo.of(entity.getPhone()),
                entity.getGender(),
                entity.getDob(),
                CustomerStatus.valueOf(entity.getStatus()),
                KycStatus.valueOf(entity.getKycStatus()),
                null, null, entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
