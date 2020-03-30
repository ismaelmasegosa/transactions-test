package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.filters;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.TransactionRepository;
import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.List;

public interface TransactionFilter {

  boolean condition();

  List<TransactionEntity> action(TransactionRepository transactionRepository);
}




