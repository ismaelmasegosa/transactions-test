package com.ismaelmasegosa.transaction.challenge.domain.transaction;

import java.util.List;

public interface TransactionCollection {

  Transaction addTransaction(Transaction transaction);

  List<Transaction> findAll();

  List<Transaction> findByAccountIban(String accountIban);


}
