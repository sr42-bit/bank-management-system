package com.corebanking.engine.domain.model.enums;

public enum AccountType {

    SAVINGS(false),
    CURRENT(true);

    private final boolean overdraftAllowed;

    AccountType(boolean overdraftAllowed) {
        this.overdraftAllowed = overdraftAllowed;
    }

    public boolean isOverdraftAllowed() {
        return overdraftAllowed;
    }
}