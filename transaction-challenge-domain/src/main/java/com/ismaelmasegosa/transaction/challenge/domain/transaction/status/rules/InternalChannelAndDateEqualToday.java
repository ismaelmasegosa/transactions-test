package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.INTERNAL;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.PENDING;
import static java.time.Instant.ofEpochMilli;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class InternalChannelAndDateEqualToday implements StatusRules {

  private Transaction transaction;

  public InternalChannelAndDateEqualToday(Transaction transaction, String channel) {
    this.transaction = transaction;
    this.channel = channel;
  }

  private String channel;

  @Override
  public boolean condition() {
    return isEqualsToday(transaction.getDate()) && isInternal();
  }

  @Override
  public TransactionStatus action() {
    return new TransactionStatus(transaction.getReference(), PENDING.name(), transaction.getAmount(), transaction.getFee());
  }

  private boolean isInternal() {
    return channel.equalsIgnoreCase(INTERNAL.name());
  }

  private boolean isEqualsToday(long transactionDate) {
    LocalDate date = LocalDate.from(LocalDateTime.ofInstant(ofEpochMilli(transactionDate), ZoneId.of("UTC")));
    return date.isEqual(LocalDate.now());
  }
}
