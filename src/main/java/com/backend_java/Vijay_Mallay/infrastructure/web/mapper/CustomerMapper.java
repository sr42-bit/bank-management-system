package com.backend_java.Vijay_Mallay.infrastructure.web.mapper;

import com.backend_java.Vijay_Mallay.domain.model.Customer;
import com.backend_java.Vijay_Mallay.infrastructure.persistence.entity.CustomerJpaEntity;
import com.backend_java.Vijay_Mallay.infrastructure.web.dto.request.CustomerRequestDto;
import com.backend_java.Vijay_Mallay.infrastructure.web.dto.response.CustomerResponseDto;
import java.time.LocalDateTime;

/**
 * MAPPER — converts between DTO ↔ Domain ↔ Entity
 */
public final class CustomerMapper {

    private CustomerMapper() {}

    /* Domain → JPA Entity */
    public static CustomerJpaEntity toEntity(Customer d) {
        return CustomerJpaEntity.builder()
                .fullName(d.getFullName())
                .gender(d.getGender())
                .dateOfBirth(d.getDateOfBirth())
                .email(d.getEmail())
                .phoneNumber(d.getPhoneNumber())
                .panNumber(d.getPanNumber())
                .aadharNumber(d.getAadharNumber())
                .kycVerified(d.isKycVerified())
                .addressLine1(d.getAddressLine1())
                .addressLine2(d.getAddressLine2())
                .city(d.getCity())
                .state(d.getState())
                .postalCode(d.getPostalCode())
                .country(d.getCountry())
                .active(d.isActive())
                .createdAt(d.getCreatedDate())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    /* JPA Entity → Domain */
    public static Customer toDomain(CustomerJpaEntity e) {
        return Customer.builder()
                .customerId("CUST-" + e.getId())
                .fullName(e.getFullName())
                .gender(e.getGender())
                .dateOfBirth(e.getDateOfBirth())
                .email(e.getEmail())
                .phoneNumber(e.getPhoneNumber())
                .panNumber(e.getPanNumber())
                .aadharNumber(e.getAadharNumber())
                .kycVerified(e.isKycVerified())
                .addressLine1(e.getAddressLine1())
                .addressLine2(e.getAddressLine2())
                .city(e.getCity())
                .state(e.getState())
                .postalCode(e.getPostalCode())
                .country(e.getCountry())
                .active(e.isActive())
                .createdDate(e.getCreatedAt())
                .lastUpdated(e.getUpdatedAt())
                .build();
    }

    /* DTO → Domain */
    public static Customer toDomain(CustomerRequestDto r) {
        return Customer.builder()
                .fullName(r.getFullName())
                .gender(r.getGender())
                .dateOfBirth(r.getDateOfBirth())
                .email(r.getEmail())
                .phoneNumber(r.getPhoneNumber())
                .panNumber(r.getPanNumber())
                .aadharNumber(r.getAadharNumber())
                .addressLine1(r.getAddressLine1())
                .addressLine2(r.getAddressLine2())
                .city(r.getCity())
                .state(r.getState())
                .postalCode(r.getPostalCode())
                .country(r.getCountry())
                .active(true)
                .build();
    }

    /* Domain → Response DTO */
    public static CustomerResponseDto toResponse(Customer d) {
        return CustomerResponseDto.builder()
                .customerId(d.getCustomerId())
                .fullName(d.getFullName())
                .gender(d.getGender())
                .dateOfBirth(d.getDateOfBirth())
                .age(d.getAge())
                .email(d.getEmail())
                .phoneNumber(d.getPhoneNumber())
                .kycVerified(d.isKycVerified())
                .addressLine1(d.getAddressLine1())
                .addressLine2(d.getAddressLine2())
                .city(d.getCity())
                .state(d.getState())
                .postalCode(d.getPostalCode())
                .country(d.getCountry())
                .active(d.isActive())
                .createdDate(d.getCreatedDate())
                .lastUpdated(d.getLastUpdated())
                .build();
    }
}
