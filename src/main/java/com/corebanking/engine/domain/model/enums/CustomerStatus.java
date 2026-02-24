package com.corebanking.engine.domain.model.enums;

public enum CustomerStatus {

    ACTIVE {
        @Override
        public boolean canSuspend() { return true; }

        @Override
        public boolean canClose() { return true; }
    },

    SUSPENDED {
        @Override
        public boolean canActivate() { return true; }
    },

    INACTIVE;

    public boolean canActivate() { return false; }
    public boolean canSuspend() { return false; }
    public boolean canClose() { return false; }
}