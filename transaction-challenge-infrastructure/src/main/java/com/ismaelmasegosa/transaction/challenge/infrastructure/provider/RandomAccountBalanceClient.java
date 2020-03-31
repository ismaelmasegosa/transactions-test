package com.ismaelmasegosa.transaction.challenge.infrastructure.provider;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceClient;
import com.ismaelmasegosa.transaction.challenge.infrastructure.account.balance.provider.AccountBalanceProvider;
import org.springframework.stereotype.Component;

@Component
public class RandomAccountBalanceClient implements AccountBalanceClient {

  private final AccountBalanceProvider accountBalanceProvider;

  public RandomAccountBalanceClient(AccountBalanceProvider accountBalanceProvider) {
    this.accountBalanceProvider = accountBalanceProvider;
  }

  @Override
  public double getAccountBalance(String accountIban) {
    return accountBalanceProvider.getAccountBalance(accountIban);
  }

  @Override
  public double updateAccountBalance(String accountIban, double amount) {
    return accountBalanceProvider.updateAccountBalance(accountIban, amount);
  }
}
