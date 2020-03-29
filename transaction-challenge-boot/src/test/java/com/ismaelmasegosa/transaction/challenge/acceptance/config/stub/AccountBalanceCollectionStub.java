package com.ismaelmasegosa.transaction.challenge.acceptance.config.stub;

import com.ismaelmasegosa.transaction.challenge.domain.account.AccountBalanceRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AccountBalanceCollectionStub implements AccountBalanceRepository {

  private final Map<String, Double> balanceRepository;

  public AccountBalanceCollectionStub() {
    balanceRepository = new HashMap<>();
  }

  public double getAccountBalance(String accountIban) {
    if (balanceRepository.get(accountIban) == null) {
      double MIN_LIMIT = 1D;
      double MAX_LIMIT = 999D;
      double randomBalance = MIN_LIMIT + new Random().nextDouble() * (MAX_LIMIT - MIN_LIMIT);
      balanceRepository.put(accountIban, randomBalance);
    }
    return balanceRepository.get(accountIban);
  }

  public double updateAccountBalance(String accountIban, double account) {
    double accountBalance = getAccountBalance(accountIban);
    double finalBalance = accountBalance + account;
    balanceRepository.put(accountIban, finalBalance);
    return finalBalance;
  }
}
