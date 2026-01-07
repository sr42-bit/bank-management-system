package com.corebanking.engine.domain.model.valueobject;

import java.time.Instant;
import java.util.Objects;

public final class DomainTime {

    private final Instant value;

    private DomainTime(Instant value) {
        this.value = Objects.requireNonNull(value);
    }

    public static DomainTime of(Instant instant) {
        return new DomainTime(instant);
    }

    public Instant value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainTime)) return false;
        DomainTime that = (DomainTime) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
