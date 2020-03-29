package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.mapper;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TransactionDomainToTransactionEntity implements Function<Transaction, TransactionEntity> {

  @Override
  public TransactionEntity apply(Transaction transaction) {
    return TransactionEntity.builder().reference(transaction.getReference()).accountIban(transaction.getAccountIban())
        .date(transaction.getDate()).amount(transaction.getAmount()).fee(transaction.getFee()).description(transaction.getDescription())
        .build();
  }
}
