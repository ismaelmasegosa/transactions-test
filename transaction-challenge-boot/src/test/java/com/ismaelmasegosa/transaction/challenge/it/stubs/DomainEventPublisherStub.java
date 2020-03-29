package com.ismaelmasegosa.transaction.challenge.it.stubs;

import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEvent;
import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEventPublisher;
import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisherStub implements DomainEventPublisher {

  List<DomainEvent> events = new ArrayList<>();

  public DomainEventPublisherStub() {
  }

  @Override
  public void publish(DomainEvent event) {
    events.add(event);
  }

  public List<DomainEvent> getEvents() {
    return events;
  }
}
