package com.corebanking.engine.domain.model.enums;

public enum CustomerStatus {

    ACTIVE {
        @Override
        public boolean canClose() {
            return true;
        }

        @Override
        public boolean canSuspend() {
            return true;
        }
    },

    SUSPENDED {
        @Override
        public boolean canActivate() {
            return true;
        }

        @Override
        public boolean canClose() {
            return false;
        }
    },

    INACTIVE {
        @Override
        public boolean canActivate() {
            return false;
        }

        @Override
        public boolean canClose() {
            return false;
        }

        @Override
        public boolean canSuspend() {
            return false;
        }
    };

    public boolean canActivate() { return false; }
    public boolean canSuspend() { return false; }
    public boolean canClose() { return false; }
}
