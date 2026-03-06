package com.corebanking.engine.domain.model.valueobject;

import java.util.regex.Pattern;

public final class AccountNo {

    private static final Pattern FORMAT = Pattern.compile("^\\d{12}$");

    private final String value;

    private AccountNo(String value) {
        this.value = value;
    }

    public static AccountNo of(String raw) {
        if (raw == null)
            throw new IllegalArgumentException("AccountNo cannot be null");

        String trimmed = raw.trim();

        if (trimmed.isBlank())
            throw new IllegalArgumentException("AccountNo cannot be empty");

        if (!FORMAT.matcher(trimmed).matches())
            throw new IllegalArgumentException(
                    "AccountNo must be exactly 12 digits"
            );

        return new AccountNo(trimmed);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountNo that)) return false;
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