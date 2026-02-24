package com.corebanking.engine.domain.model.valueobject;

import com.corebanking.engine.domain.model.exception.DomainValidationException;
import java.util.Objects;

public final class CustomerId {

    private final String value;

    private CustomerId(String value) {
        this.value = Objects.requireNonNull(value, "CustomerId cannot be null").trim();
        if (this.value.isBlank()) {
            throw new DomainValidationException("CustomerId cannot be null or empty");
        }
    }

    public static CustomerId of(String raw) {
        if (raw == null) {
            throw new DomainValidationException("CustomerId cannot be null");
        }
        return new CustomerId(raw);
    }

    public String value() {
        return value; // guaranteed non-null by constructor invariant
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