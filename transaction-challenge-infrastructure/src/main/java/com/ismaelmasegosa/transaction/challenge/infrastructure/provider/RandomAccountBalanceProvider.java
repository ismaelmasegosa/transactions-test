package com.ismaelmasegosa.transaction.challenge.infrastructure.provider;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.infrastructure.balance.AccountBalanceCollection;
import org.springframework.stereotype.Component;

@Component
public class RandomAccountBalanceProvider implements AccountBalanceProvider {

  private final AccountBalanceCollection accountBalanceCollection;

  public RandomAccountBalanceProvider(AccountBalanceCollection accountBalanceCollection) {
    this.accountBalanceCollection = accountBalanceCollection;
  }

  @Override
  public double updateAccountBalance(String accountIban, double amount) {
    return accountBalanceCollection.updateAccountBalance(accountIban, amount);
  }
}
