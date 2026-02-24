package com.corebanking.engine.domain.model.valueobject;

import com.corebanking.engine.domain.model.exception.DomainValidationException;
import java.util.Objects;

public final class KycDocument {

    private final String aadhaar;
    private final String pan;

    private KycDocument(String aadhaar, String pan) {

        if (aadhaar == null || !aadhaar.matches("\\d{12}"))
            throw new DomainValidationException("Invalid Aadhaar number");

        if (pan == null || !pan.trim().toUpperCase().matches("[A-Z]{5}[0-9]{4}[A-Z]"))
            throw new DomainValidationException("Invalid PAN format");

        this.aadhaar = aadhaar;
        this.pan = pan.trim().toUpperCase();
    }

    public static KycDocument of(String aadhaar, String pan) {
        return new KycDocument(aadhaar, pan);
    }

    public String aadhaar() { return aadhaar; }
    public String pan() { return pan; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KycDocument)) return false;
        KycDocument other = (KycDocument) o;
        return aadhaar.equals(other.aadhaar) && pan.equals(other.pan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aadhaar, pan);
    }
}