package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.domain.transaction.TransactionCollection;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTransactionCollection implements TransactionCollection {

  private final TransactionRepository transactionRepository;

  public InMemoryTransactionCollection(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Override
  public Transaction addTransaction(Transaction transaction) {
    TransactionEntity transactionEntity =
        new TransactionEntity(transaction.getReference(), transaction.getAccountIban(), transaction.getDate(), transaction.getAmount(),
            transaction.getFee(), transaction.getDescription());
    transactionRepository.save(transactionEntity);
    return transaction;
  }
}
