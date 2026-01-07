package com.corebanking.engine.domain.model.valueobject;

import java.util.Objects;

public final class CustomerId {

    private final String value;

    private CustomerId(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("CustomerId cannot be null or empty");
        this.value = value;
    }

    public static CustomerId of(String value) {
        return new CustomerId(value.trim());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerId)) return false;
        CustomerId other = (CustomerId) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
