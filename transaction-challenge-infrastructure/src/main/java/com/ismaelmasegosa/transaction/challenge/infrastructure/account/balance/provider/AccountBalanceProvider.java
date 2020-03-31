package com.ismaelmasegosa.transaction.challenge.infrastructure.account.balance.provider;

public interface AccountBalanceProvider {

  double getAccountBalance(String accountIban);

  double updateAccountBalance(String accountIban, double account);
}
