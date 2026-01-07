package com.corebanking.engine.infrastructure.event;

import com.corebanking.engine.application.port.out.DomainEventPublisher;
import com.corebanking.engine.domain.model.event.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleDomainEventPublisher implements DomainEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(SimpleDomainEventPublisher.class);

    @Override
    public void publish(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            log.info("DOMAIN EVENT: {} at {}", 
                    event.getClass().getSimpleName(),
                    event.occurredAt());
        }
    }
}
