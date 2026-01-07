package com.corebanking.engine.domain.model.valueobject;

import java.util.Objects;

public final class PhoneNo {

    private static final String E164_REGEX = "^\\+[1-9]\\d{7,14}$";

    private final String value;

    private PhoneNo(String value) {

        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Phone number cannot be empty");

        String normalized = value.trim();

        if (!normalized.matches(E164_REGEX))
            throw new IllegalArgumentException("Phone number must be in E.164 format (e.g. +919876543210)");

        this.value = normalized;
    }

    public static PhoneNo of(String value) {
        return new PhoneNo(value);
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNo)) return false;
        PhoneNo other = (PhoneNo) o;
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
