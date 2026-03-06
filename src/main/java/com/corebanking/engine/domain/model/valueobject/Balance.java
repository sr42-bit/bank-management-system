package com.corebanking.engine.domain.model.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Balance {

    private static final int SCALE = 2; // ₹ format

    private final BigDecimal amount;

    private Balance(BigDecimal amount) {
        this.amount = amount.setScale(SCALE, RoundingMode.HALF_UP);
    }

    public static Balance of(BigDecimal amount) {
        Objects.requireNonNull(amount);
        return new Balance(amount);
    }

    public static Balance zero() {
        return new Balance(BigDecimal.ZERO);
    }

    public BigDecimal amount() {
        return amount;
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isLessThan(Balance other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public Balance add(Balance other) {
        return new Balance(this.amount.add(other.amount));
    }

    public Balance subtract(Balance other) {
        return new Balance(this.amount.subtract(other.amount));
    }
    public BigDecimal value() {
    return amount;
}

    @Override
    public String toString() {
        return amount.toString();
    }
}