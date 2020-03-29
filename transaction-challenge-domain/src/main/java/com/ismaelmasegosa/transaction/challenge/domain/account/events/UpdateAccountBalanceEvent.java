package com.ismaelmasegosa.transaction.challenge.domain.account.events;

import com.ismaelmasegosa.transaction.challenge.domain.events.DomainEvent;
import java.util.Objects;

public class UpdateAccountBalanceEvent extends DomainEvent {

  private final String accountIban;

  private final double balance;

  public UpdateAccountBalanceEvent(String accountIban, double balance) {
    super("UPDTADE_BALANCE");
    this.accountIban = accountIban;
    this.balance = balance;
  }

  public String getAccountIban() {
    return accountIban;
  }

  public double getBalance() {
    return balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    UpdateAccountBalanceEvent that = (UpdateAccountBalanceEvent) o;
    return Double.compare(that.balance, balance) == 0 && Objects.equals(accountIban, that.accountIban);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), accountIban, balance);
  }

  @Override
  public String toString() {
    return "UpdateAccountBalanceEvent{" + "accountIban='" + accountIban + '\'' + ", balance=" + balance + '}';
  }
}
