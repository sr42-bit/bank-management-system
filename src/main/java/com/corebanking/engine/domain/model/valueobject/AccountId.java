package com.corebanking.engine.domain.model.valueobject;

import java.util.Objects;

public final class AccountId {

    private final String value;

    private AccountId(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("AccountId cannot be null or empty");
        this.value = value;
    }

    public static AccountId of(String value) {
        return new AccountId(value.trim());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId that = (AccountId) o;
        return value.equals(that.value);
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
