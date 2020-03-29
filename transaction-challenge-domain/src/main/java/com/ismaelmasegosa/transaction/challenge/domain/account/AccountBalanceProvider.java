package com.ismaelmasegosa.transaction.challenge.domain.account;

public interface AccountBalanceProvider {

  double getAccountBalance(String accountIban);

  double updateAccountBalance(String accountIban, double amount);

}
