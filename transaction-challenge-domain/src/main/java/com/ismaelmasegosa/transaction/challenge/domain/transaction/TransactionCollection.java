package com.ismaelmasegosa.transaction.challenge.domain.transaction;

import java.util.List;

public interface TransactionCollection {

  Transaction addTransaction(Transaction transaction);

  List<Transaction> findByAccountIbanOrderByAmount(String iban, int sort);

}
