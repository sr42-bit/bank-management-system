package com.corebanking.engine.domain.model.valueobject;

import com.corebanking.engine.domain.model.exception.DomainValidationException;
import java.time.Instant;
import java.util.Objects;

public final class DomainTime {

    private final Instant value;

    private DomainTime(Instant value) {
        if (value == null)
            throw new DomainValidationException("DomainTime cannot be null");
        this.value = value;
    }

    public static DomainTime now() {
        return new DomainTime(Instant.now());
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