package com.ismaelmasegosa.transaction.challenge.infrastructure.provider;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceProvider;
import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceRepository;
import org.springframework.stereotype.Component;

@Component
public class RandomAccountBalanceProvider implements AccountBalanceProvider {

  private final AccountBalanceRepository accountBalanceRepository;

  public RandomAccountBalanceProvider(AccountBalanceRepository accountBalanceRepository) {
    this.accountBalanceRepository = accountBalanceRepository;
  }

  @Override
  public double getAccountBalance(String accountIban) {
    return accountBalanceRepository.getAccountBalance(accountIban);
  }

  @Override
  public double updateAccountBalance(String accountIban, double amount) {
    return accountBalanceRepository.updateAccountBalance(accountIban, amount);
  }
}
