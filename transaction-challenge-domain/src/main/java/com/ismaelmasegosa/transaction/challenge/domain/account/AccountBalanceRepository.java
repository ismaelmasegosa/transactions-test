package com.ismaelmasegosa.transaction.challenge.domain.account;

public interface AccountBalanceRepository {

  double getAccountBalance(String accountIban);

  double updateAccountBalance(String accountIban, double account);
}
