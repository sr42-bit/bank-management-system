package com.corebanking.engine.application.port.out;

import java.util.List;

import com.corebanking.engine.domain.model.event.DomainEvent;

public interface DomainEventPublisher {
    void publish(List<DomainEvent> events);
}
