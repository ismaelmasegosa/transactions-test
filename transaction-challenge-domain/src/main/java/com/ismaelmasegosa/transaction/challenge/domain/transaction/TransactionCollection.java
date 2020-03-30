package com.ismaelmasegosa.transaction.challenge.domain.transaction;

import java.util.List;

public interface TransactionCollection {

  Transaction addTransaction(Transaction transaction);

  List<Transaction> findByAccountIban(String accountIban);

  List<Transaction> findOrderByAmount(String sort);

  List<Transaction> findByAccountIbanOrderByAmount(String accountIban, String sort);

}
