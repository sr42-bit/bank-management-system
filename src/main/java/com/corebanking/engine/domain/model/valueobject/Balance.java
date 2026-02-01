package com.corebanking.engine.domain.model.valueobject;

public final class Balance {

    private final double amount;

    private Balance(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.amount = amount;
    }

    public static Balance of(double amount) {
        return new Balance(amount);
    }

    public double amount() {
        return amount;
    }

    public boolean isZero() {
        return amount == 0.0;
    }

    public boolean isLessThan(Balance other) {
        return this.amount < other.amount;
    }

    public Balance add(Balance other) {
        return new Balance(this.amount + other.amount);
    }

    public Balance subtract(Balance other) {
        if (this.amount < other.amount) {
            throw new IllegalStateException("Insufficient balance");
        }
        return new Balance(this.amount - other.amount);
    }
}
