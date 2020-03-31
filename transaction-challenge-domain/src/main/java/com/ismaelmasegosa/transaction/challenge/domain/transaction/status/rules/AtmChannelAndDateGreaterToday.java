package com.ismaelmasegosa.transaction.challenge.domain.transaction.status.rules;

import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Channel.ATM;
import static com.ismaelmasegosa.transaction.challenge.domain.transaction.status.Status.PENDING;
import static java.time.Instant.ofEpochMilli;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.status.TransactionStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AtmChannelAndDateGreaterToday implements StatusRules {

  private Transaction transaction;

  public AtmChannelAndDateGreaterToday(Transaction transaction, String channel) {
    this.transaction = transaction;
    this.channel = channel;
  }

  private String channel;

  @Override
  public boolean condition() {
    return isGreaterToday(transaction.getDate()) && isAtm();
  }

  @Override
  public TransactionStatus action() {
    double amount = transaction.getAmount() - transaction.getFee();
    return new TransactionStatus(transaction.getReference(), PENDING.name(), amount, 0.0);
  }

  private boolean isAtm() {
    return channel.equalsIgnoreCase(ATM.name());
  }

  private boolean isGreaterToday(long transactionDate) {
    LocalDate date = LocalDate.from(LocalDateTime.ofInstant(ofEpochMilli(transactionDate), ZoneId.of("UTC")));
    return date.isAfter(LocalDate.now());
  }
}
