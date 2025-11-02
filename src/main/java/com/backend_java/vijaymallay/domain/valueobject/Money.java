package com.backend_java.vijaymallay.domain.valueobject;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
/**
 * VALUE OBJECT
 * Represents an immutable monetary amount with currency.
 * Used for all financial fields in domain models (Account, Transaction, etc.)
 */
public final class Money {

    private final BigDecimal amount;
    private final Currency currency;

    // Private constructor — use factory methods to create
    private Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // ✅ Factory method (cleaner way to create Money)
    public static Money of(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

    // ✅ Getter methods
    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    // ✅ Domain-safe arithmetic operations
    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        ensureSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public boolean isGreaterThan(Money other) {
        ensureSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

        public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }
    
    // ✅ Ensures we don’t mix different currencies
    private void ensureSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: " +
                this.currency + " vs " + other.currency);
        }
    }

    // ✅ Override equals and hashCode (important for Value Objects)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0 && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return currency.getSymbol() + " " + amount;
    }
}
