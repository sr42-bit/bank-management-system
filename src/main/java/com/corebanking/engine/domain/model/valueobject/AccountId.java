package com.corebanking.engine.domain.model.valueobject;

import java.util.regex.Pattern;

public final class AccountId {

    private static final Pattern FORMAT =
            Pattern.compile("^ACC-[A-Z0-9]{10,20}$");

    private final String value;

    private AccountId(String value) {
        this.value = value;
    }

    public static AccountId of(String raw) {
        if (raw == null)
            throw new IllegalArgumentException("AccountId cannot be null");

        String trimmed = raw.trim();

        if (trimmed.isBlank())
            throw new IllegalArgumentException("AccountId cannot be empty");

        if (trimmed.length() > 30)
            throw new IllegalArgumentException("AccountId too long");

        if (!FORMAT.matcher(trimmed).matches())
            throw new IllegalArgumentException(
                    "Invalid AccountId format. Expected: ACC-XXXXXXXXXX"
            );

        return new AccountId(trimmed);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId that)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}