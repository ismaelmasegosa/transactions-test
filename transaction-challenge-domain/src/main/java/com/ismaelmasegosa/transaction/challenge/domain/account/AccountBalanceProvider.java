package com.ismaelmasegosa.transaction.challenge.domain.account;

public interface AccountBalanceProvider {

  double updateAccountBalance(String accountIban, double amount);

}
