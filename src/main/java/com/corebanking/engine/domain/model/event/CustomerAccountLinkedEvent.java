package com.corebanking.engine.domain.model.event;

import com.corebanking.engine.domain.model.valueobject.*;


public record CustomerAccountLinkedEvent(
        CustomerId customerId,
        AccountId accountId,
        DomainTime occurredAt
      
) implements DomainEvent {}
