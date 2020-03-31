package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.CLIENT;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.FUTURE;
import static java.time.Instant.ofEpochMilli;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClientChannelAndDateGreaterToday implements StatusRules {

  private Transaction transaction;

  public ClientChannelAndDateGreaterToday(Transaction transaction, String channel) {
    this.transaction = transaction;
    this.channel = channel;
  }

  private String channel;

  @Override
  public boolean condition() {
    return isGreaterToday(transaction.getDate()) && isClient();
  }

  @Override
  public TransactionStatus action() {
    double amount = transaction.getAmount() - transaction.getFee();
    return new TransactionStatus(transaction.getReference(), FUTURE.name(), amount, 0.0);
  }

  private boolean isClient() {
    return channel.equalsIgnoreCase(CLIENT.name());
  }

  private boolean isGreaterToday(long transactionDate) {
    LocalDate date = LocalDate.from(LocalDateTime.ofInstant(ofEpochMilli(transactionDate), ZoneId.of("UTC")));
    return date.isAfter(LocalDate.now());
  }
}
