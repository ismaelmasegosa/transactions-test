package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.ATM;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.CLIENT;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.PENDING;
import static java.time.Instant.ofEpochMilli;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClientOrAtmChannelAndDateEqualsToday implements StatusRules {

  private Transaction transaction;

  public ClientOrAtmChannelAndDateEqualsToday(Transaction transaction, String channel) {
    this.transaction = transaction;
    this.channel = channel;
  }

  private String channel;

  @Override
  public boolean condition() {
    return isEqualsToday(transaction.getDate()) && isClient();
  }

  @Override
  public TransactionStatus action() {
    double amount = transaction.getAmount() - transaction.getFee();
    return new TransactionStatus(transaction.getReference(), PENDING.name(), amount, 0.0);
  }

  private boolean isClient() {
    return channel.equalsIgnoreCase(CLIENT.name()) || channel.equalsIgnoreCase(ATM.name());
  }

  private boolean isEqualsToday(long transactionDate) {
    LocalDate date = LocalDate.from(LocalDateTime.ofInstant(ofEpochMilli(transactionDate), ZoneId.of("UTC")));
    return date.isEqual(LocalDate.now());
  }
}
