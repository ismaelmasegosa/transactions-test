package com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction;

import com.ismaelmasegosa.transaction.challenge.infrastructure.persistence.transaction.entities.TransactionEntity;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

  Optional<TransactionEntity> findByReference(String reference);

  List<TransactionEntity> findAll();

  List<TransactionEntity> findByAccountIban(String accountIban);

  List<TransactionEntity> findOrderByAmount(String sort);

  List<TransactionEntity> findByAccountIbanOrderByAmount(String accountIban, String sort);

  TransactionEntity save(TransactionEntity transactionEntity);

  void delete(String reference);
}
