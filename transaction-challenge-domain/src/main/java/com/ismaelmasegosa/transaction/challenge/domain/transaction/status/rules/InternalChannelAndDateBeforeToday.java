package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.INTERNAL;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.SETTLED;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class InternalChannelAndDateBeforeToday implements StatusRules {

  private Transaction transaction;

  public InternalChannelAndDateBeforeToday(Transaction transaction, String channel) {
    this.transaction = transaction;
    this.channel = channel;
  }

  private String channel;

  @Override
  public boolean condition() {
    return isInternal() && isBeforeToday(transaction.getDate());
  }

  @Override
  public TransactionStatus action() {
    return new TransactionStatus(transaction.getReference(), SETTLED.name(), transaction.getAmount(), transaction.getFee());
  }

  private boolean isInternal() {
    return channel.equalsIgnoreCase(INTERNAL.name());
  }

  private boolean isBeforeToday(long transactionDate) {
    return LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() > transactionDate;
  }
}
