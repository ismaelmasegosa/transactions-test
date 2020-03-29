package com.ismaelmasegosa.transaction.challenge.domain.events;

public interface DomainEventPublisher {

  void publish(DomainEvent event);
}
