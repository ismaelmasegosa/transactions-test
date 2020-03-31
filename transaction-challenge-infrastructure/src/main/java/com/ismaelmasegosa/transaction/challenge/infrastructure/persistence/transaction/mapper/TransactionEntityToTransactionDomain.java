package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.mapper;

import com.ismaelmasegosa.transaction.challenge.domain.transaction.Transaction;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityToTransactionDomain implements Function<TransactionEntity, Transaction> {

  @Override
  public Transaction apply(TransactionEntity transactionEntity) {
    return new Transaction(transactionEntity.getReference(), transactionEntity.getAccountIban(), transactionEntity.getDate(),
        transactionEntity.getAmount(), transactionEntity.getFee(), transactionEntity.getDescription());
  }
}
