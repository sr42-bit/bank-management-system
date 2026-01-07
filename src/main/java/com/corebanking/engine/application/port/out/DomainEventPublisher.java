package com.corebanking.engine.application.port.out;

import com.corebanking.engine.domain.model.event.DomainEvent;
import java.util.List;

public interface DomainEventPublisher {
    void publish(List<DomainEvent> events);
}
