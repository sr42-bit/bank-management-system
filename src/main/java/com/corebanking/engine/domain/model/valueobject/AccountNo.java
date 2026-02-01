package com.corebanking.engine.domain.model.valueobject;

import java.util.Objects;

public class AccountNo{
    private final String value;
      private AccountNo(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("AccountNo cannot be null or empty");
        this.value = value;
    }

    public static AccountNo of(String value) {
        return new AccountNo(value.trim());
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountNo)) return false;
        AccountNo that = (AccountNo) o;
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