package com.corebanking.engine.domain.model.valueobject;

import com.corebanking.engine.domain.model.exception.DomainValidationException;
import java.util.Objects;

public final class EmailAddress {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final String value;

    private EmailAddress(String value) {
        if (value == null || value.isBlank())
            throw new DomainValidationException("Email cannot be empty");

        String normalized = value.trim().toLowerCase();

        if (!normalized.matches(EMAIL_REGEX))
            throw new DomainValidationException("Invalid email format");

        this.value = normalized;
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailAddress)) return false;
        EmailAddress other = (EmailAddress) o;
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