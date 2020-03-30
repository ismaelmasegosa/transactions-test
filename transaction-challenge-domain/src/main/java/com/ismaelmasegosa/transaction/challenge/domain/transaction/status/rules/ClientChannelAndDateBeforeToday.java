package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.CLIENT;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.SETTLED;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ClientChannelAndDateBeforeToday implements StatusRules {

  private Transaction transaction;

  public ClientChannelAndDateBeforeToday(Transaction transaction, String channel) {
    this.transaction = transaction;
    this.channel = channel;
  }

  private String channel;

  @Override
  public boolean condition() {
    return channel.equalsIgnoreCase(CLIENT.name()) && isBeforeToday(transaction.getDate());
  }

  @Override
  public TransactionStatus action() {
    double amount = transaction.getAmount() - transaction.getFee();
    return new TransactionStatus(transaction.getReference(), SETTLED.name(), amount, 0.0);
  }

  private boolean isBeforeToday(long transactionDate) {
    return LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() > transactionDate;
  }
}
