package com.corebanking.engine.domain.model.event;

import com.corebanking.engine.domain.model.valueobject.CustomerId;
import com.corebanking.engine.domain.model.valueobject.DomainTime;

public record CustomerClosedEvent(CustomerId customerId, DomainTime occurredAt)
        implements DomainEvent {}
