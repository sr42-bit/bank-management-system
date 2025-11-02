package com.backend_java.vijaymallay.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Immutable Value Object representing Customer KYC details.
 */
@Getter
@EqualsAndHashCode
@ToString
public final class Kyc {

    private final String adharCardNumber;
    private final String panCardNumber;
    private final String voterIdNumber;
    private final String drivingLicenseNumber;
    private final boolean verified;

    // Private constructor ensures immutability
    private Kyc(String adharCardNumber, String panCardNumber, String voterIdNumber, String drivingLicenseNumber, boolean verified) {
        this.adharCardNumber = adharCardNumber;
        this.panCardNumber = panCardNumber;
        this.voterIdNumber = voterIdNumber;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.verified = verified;
        validate();
    }

    /**
     * Factory method to create a new Kyc object.
     */
    public static Kyc create(String adharCardNumber, String panCardNumber, String voterIdNumber, String drivingLicenseNumber, boolean verified) {
        return new Kyc(adharCardNumber, panCardNumber, voterIdNumber, drivingLicenseNumber, verified);
    }

    /**
     * Returns a new Kyc instance marked as verified.
     */
    public Kyc markVerified() {
        return new Kyc(this.adharCardNumber, this.panCardNumber, this.voterIdNumber, this.drivingLicenseNumber, true);
    }

    private void validate() {
        if (adharCardNumber == null || adharCardNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid Aadhar card number");
        }
        if (panCardNumber == null || panCardNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid PAN card number");
        }
        if (voterIdNumber == null) {
            throw new IllegalArgumentException("Voter ID number cannot be null");
        }
        if (drivingLicenseNumber == null) {
            throw new IllegalArgumentException("Driving license number cannot be null");
        }
    }
}
