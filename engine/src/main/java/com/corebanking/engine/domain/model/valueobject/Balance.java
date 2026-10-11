package com.corebanking.engine.domain.model.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Balance {
    private static final int SCALE = 2;
    private final BigDecimal amount;

    private Balance(BigDecimal amount) {
        this.amount = amount.setScale(SCALE, RoundingMode.HALF_UP);
    }

    /** Constructs a non-negative balance. */
    public static Balance of(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount is required");
        BigDecimal normalized = amount.setScale(SCALE, RoundingMode.HALF_UP);
        if (normalized.signum() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        return new Balance(normalized);
    }

    /** Constructs a strictly positive amount for deposits, withdrawals, and transfers. */
    public static Balance positiveAmount(BigDecimal amount) {
        Balance value = of(amount);
        if (value.isZero()) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        return value;
    }

    public static Balance zero() { return new Balance(BigDecimal.ZERO); }
    public BigDecimal amount() { return amount; }
    public BigDecimal value() { return amount; }
    public boolean isZero() { return amount.compareTo(BigDecimal.ZERO) == 0; }
    public boolean isNegative() { return amount.compareTo(BigDecimal.ZERO) < 0; }

    public boolean isLessThan(Balance other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public Balance add(Balance other) {
        Objects.requireNonNull(other, "Other amount is required");
        return new Balance(this.amount.add(other.amount));
    }

    public Balance subtract(Balance other) {
        Objects.requireNonNull(other, "Other amount is required");
        // Account.withdraw enforces overdraft policy.
        return new Balance(this.amount.subtract(other.amount));
    }

    @Override
    public String toString() { return amount.toString(); }
}