package com.ismaelmasegosa.transaction.challenge.domain.events;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public abstract class DomainEvent {

  private final long happenedAt;

  private final String id;

  private final String eventCode;

  public DomainEvent(String eventCode) {
    this.eventCode = eventCode;
    this.happenedAt = Instant.now().toEpochMilli();
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public long getHappenedAt() {
    return happenedAt;
  }

  public String getEventCode() {
    return eventCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomainEvent that = (DomainEvent) o;
    return happenedAt == that.happenedAt && Objects.equals(id, that.id) && Objects.equals(eventCode, that.eventCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(happenedAt, id, eventCode);
  }

  @Override
  public String toString() {
    return "DomainEvent{" + "happenedAt=" + happenedAt + ", id='" + id + '\'' + ", eventCode='" + eventCode + '\'' + '}';
  }
}
