package com.backend_java.vijaymallay.infrastructure.web.mapper;

import com.backend_java.vijaymallay.domain.model.Customer;
import com.backend_java.vijaymallay.domain.valueobject.Address;
import com.backend_java.vijaymallay.domain.valueobject.Kyc;
import com.backend_java.vijaymallay.domain.valueobject.Status;
import com.backend_java.vijaymallay.domain.enums.Reason;
import com.backend_java.vijaymallay.infrastructure.persistence.entity.CustomerJpaEntity;
import com.backend_java.vijaymallay.infrastructure.web.dto.request.CustomerRequestDto;
import com.backend_java.vijaymallay.infrastructure.web.dto.response.CustomerResponseDto;

/**
 * CustomerMapper — Converts between DTO ↔ Domain ↔ Entity
 */
public final class CustomerMapper {

    private CustomerMapper() {}

    // ---------------- Domain → JPA Entity ----------------
    public static CustomerJpaEntity toEntity(Customer customer) {
        return CustomerJpaEntity.builder()
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .dateOfBirth(customer.getDateOfBirth())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNo())
                .panNumber(customer.getKyc().getPanCardNumber())
                .aadharNumber(customer.getKyc().getAdharCardNumber())
                .kycVerified(customer.getKyc().isVerified())
                .addressLine1(customer.getAddress().getAddressLine1())
                .addressLine2(customer.getAddress().getAddressLine2())
                .city(customer.getAddress().getCity())
                .state(customer.getAddress().getState())
                .postalCode(customer.getAddress().getPostalCode())
                .country(customer.getAddress().getCountry())
                .active(customer.getStatus().isActive())
                .createdAt(customer.getStatus().getCreatedDate())
                .updatedAt(customer.getStatus().getLastUpdated())
                .build();
    }

    // ---------------- JPA Entity → Domain ----------------
    public static Customer toDomain(CustomerJpaEntity entity) {
        Address address = Address.create(
                entity.getAddressLine1(),
                entity.getAddressLine2(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getPostalCode()
        );

        Kyc kyc = Kyc.create(
                entity.getAadharNumber(),
                entity.getPanNumber(),
                "", // voterIdNumber
                "", // drivingLicenseNumber
                entity.isKycVerified()
        );

        Status status = Status.create(
                entity.isActive(),
                "system",
                Reason.OTHER
        );

        // Customer.create constructor should match: (fullName, gender, dob, email, phoneNo, kyc, address, status)
        return Customer.create(
                entity.getFullName(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                kyc,
                address,
                status
        ).withCustomerId("CUST-" + entity.getId()); // preserves ID
    }

    // ---------------- DTO → Domain ----------------
    public static Customer toDomain(CustomerRequestDto dto) {
        Address address = Address.create(
                dto.getAddressLine1(),
                dto.getAddressLine2(),
                dto.getCity(),
                dto.getState(),
                dto.getCountry(),
                dto.getPostalCode()
        );

        Kyc kyc = Kyc.create(
                dto.getAadharNumber(),
                dto.getPanNumber(),
                "", // voterIdNumber
                "", // drivingLicenseNumber
                false // new customers are unverified
        );

        Status status = Status.create(false, "system", Reason.OTHER); // default inactive

        return Customer.create(
                dto.getFullName(),
                dto.getGender(),
                dto.getDateOfBirth(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                kyc,
                address,
                status
        );
    }

    // ---------------- Domain → Response DTO ----------------
    public static CustomerResponseDto toResponse(Customer customer) {
        return CustomerResponseDto.builder()
                .customerId(customer.getCustomerId())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .dateOfBirth(customer.getDateOfBirth())
                .age(customer.getAge())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNo())
                .kycVerified(customer.getKyc().isVerified())
                .addressLine1(customer.getAddress().getAddressLine1())
                .addressLine2(customer.getAddress().getAddressLine2())
                .city(customer.getAddress().getCity())
                .state(customer.getAddress().getState())
                .postalCode(customer.getAddress().getPostalCode())
                .country(customer.getAddress().getCountry())
                .active(customer.getStatus().isActive())
                .createdDate(customer.getStatus().getCreatedDate())
                .lastUpdated(customer.getStatus().getLastUpdated())
                .build();
    }
}
