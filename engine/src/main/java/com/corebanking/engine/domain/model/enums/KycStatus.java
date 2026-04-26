package com.corebanking.engine.domain.model.enums;

public enum KycStatus {

    NOT_STARTED {
        @Override
        public boolean canSubmit() { return true; }
    },

    PENDING_REVIEW {
        @Override
        public boolean canVerify() { return true; }

        @Override
        public boolean canReject() { return true; }
    },

    VERIFIED,

    REJECTED {
        @Override
        public boolean canResubmit() { return true; }
    };

    public boolean canSubmit()   { return false; }
    public boolean canVerify()   { return false; }
    public boolean canReject()   { return false; }
    public boolean canResubmit() { return false; }
}