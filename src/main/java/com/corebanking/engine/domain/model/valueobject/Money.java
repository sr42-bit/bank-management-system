package com.corebanking.engine.domain.model.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

    private final BigDecimal amount;
    private final String currency;

    private Money(BigDecimal amount, String currency) {

        if (amount == null || amount.scale() > 2 || amount.signum() < 0)
            throw new IllegalArgumentException("Invalid monetary amount");

        if (currency == null || !currency.matches("[A-Z]{3}"))
            throw new IllegalArgumentException("Invalid ISO currency code");

        this.amount = amount.setScale(2, RoundingMode.UNNECESSARY);
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, String currency) {
        return new Money(amount, currency);
    }

    public BigDecimal amount() { return amount; }
    public String currency() { return currency; }

    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        ensureSameCurrency(other);
        BigDecimal result = amount.subtract(other.amount);
        if (result.signum() < 0)
            throw new IllegalArgumentException("Insufficient balance");
        return new Money(result, currency);
    }

    private void ensureSameCurrency(Money other) {
        if (!currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
