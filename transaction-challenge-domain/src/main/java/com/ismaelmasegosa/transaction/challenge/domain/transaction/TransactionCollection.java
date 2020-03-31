package com.ismaelmasegosa.transaction.challenge.domain.transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionCollection {

  Optional<Transaction> findByReference(String reference);

  Transaction addTransaction(Transaction transaction);

  List<Transaction> findByAccountIbanOrderByAmount(String accountIban, String sort);

}
