package com.backend_java.vijaymallay.domain.valueobject;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class Address {
    @ToString.Include
    private final String addressLine1;
    private final String addressLine2;
    @ToString.Include
    private final String city;
    @ToString.Include
    private final String state;
    @ToString.Include
    private final String country;
    private final String postalCode;

    private Address(String addressLine1, String addressLine2, String city, String state, String country, String postalCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;

        validate();
    }

    public static Address create(String addressLine1, String addressLine2, String city, String state, String country, String postalCode) {
        return new Address(addressLine1, addressLine2, city, state, country, postalCode);
    }

    private void validate() {
        if (addressLine1 == null || addressLine1.isBlank()) throw new IllegalArgumentException("Address Line 1 cannot be empty");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("City cannot be empty");
        if (state == null || state.isBlank()) throw new IllegalArgumentException("State cannot be empty");
        if (country == null || country.isBlank()) throw new IllegalArgumentException("Country cannot be empty");
        if (postalCode == null || postalCode.isBlank()) throw new IllegalArgumentException("Postal Code cannot be empty");
    }
}
