package com.ismaelmasegosa.transaction.challenge.infrastructure.events.publisher;

import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEvent;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InMemoryEventPublisher implements DomainEventPublisher {

  private final ApplicationEventPublisher delegatedPublisher;

  public InMemoryEventPublisher(ApplicationEventPublisher delegatedPublisher) {
    this.delegatedPublisher = delegatedPublisher;
  }

  @Override
  public void publish(DomainEvent event) {
    delegatedPublisher.publishEvent(event);
  }
}
