package com.corebanking.engine.domain.model.event;

import com.corebanking.engine.domain.model.valueobject.DomainTime;

public interface DomainEvent {
    DomainTime occurredAt();
}
