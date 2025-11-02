package com.backend_java.vijaymallay.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.backend_java.vijaymallay.domain.enums.Reason;

/**
 * Immutable Value Object representing the status of an entity.
 */
@Getter
@ToString
@EqualsAndHashCode
public final class Status {

    private final boolean active;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastUpdated;
    private final String modifiedBy;
    private final Reason reason;

    // Private constructor
    private Status(boolean active, String modifiedBy, Reason reason, LocalDateTime createdDate, LocalDateTime lastUpdated) {
        this.active = active;
        this.modifiedBy = modifiedBy;
        this.reason = reason;
        this.createdDate = createdDate != null ? createdDate : LocalDateTime.now();
        this.lastUpdated = lastUpdated != null ? lastUpdated : LocalDateTime.now();

        validate();
    }

    // Factory method to create a new Status
    public static Status create(boolean active, String modifiedBy, Reason reason) {
        return new Status(active, modifiedBy, reason, LocalDateTime.now(), LocalDateTime.now());
    }

    // Activate Status (returns new instance)
    public Status activate(String modifiedBy, Reason reason) {
        return new Status(true, modifiedBy, reason, this.createdDate, LocalDateTime.now());
    }

    // Deactivate Status (returns new instance)
    public Status deactivate(String modifiedBy, Reason reason) {
        return new Status(false, modifiedBy, reason, this.createdDate, LocalDateTime.now());
    }

    // Validation
    private void validate() {
        if (modifiedBy == null || modifiedBy.isBlank()) {
            throw new IllegalArgumentException("modifiedBy cannot be null or blank");
        }
        if (reason == null) {
            throw new IllegalArgumentException("reason cannot be null");
        }
    }
}
