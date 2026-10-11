package com.corebanking.engine.domain.model.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {
    @Test
    void positiveAmountAcceptsPositiveMoneyAndNormalizesScale() {
        assertEquals(new BigDecimal("10.00"), Balance.positiveAmount(new BigDecimal("10")).value());
    }

    @Test
    void positiveAmountRejectsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> Balance.positiveAmount(new BigDecimal("0.00")));
    }

    @Test
    void positiveAmountRejectsNegativeMoney() {
        assertThrows(IllegalArgumentException.class,
                () -> Balance.positiveAmount(new BigDecimal("-1.00")));
    }

    @Test
    void balanceRejectsNegativeOpeningValue() {
        assertThrows(IllegalArgumentException.class,
                () -> Balance.of(new BigDecimal("-0.01")));
    }
}
